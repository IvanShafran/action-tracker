package me.shafran.actiontracker.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.repository.datasource.ActionDataSource
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.data.repository.datasource.EventId
import me.shafran.actiontracker.data.repository.datasource.InsertActionData
import me.shafran.actiontracker.data.repository.datasource.InsertEventData
import me.shafran.actiontracker.rx.RxSchedulers
import javax.inject.Inject

class ActionRepository @Inject constructor(
        private val schedulers: RxSchedulers,
        private val dataSource: ActionDataSource
) {

    private class ActionChanged(val actionId: Long)

    private val actionsChangedSubject: Subject<ActionChanged> =
            BehaviorSubject.create<ActionChanged>().toSerialized()

    init {
        pushActionsChanged(-1) // Push with fake id
    }

    private fun pushActionsChanged(actionId: Long) {
        actionsChangedSubject.onNext(ActionChanged(actionId))
    }

    fun getInsertActionSingle(data: InsertActionData): Single<ActionId> {
        return Single
                .fromCallable { dataSource.insertAction(data) }
                .subscribeOn(schedulers.io())
                .doOnSuccess { pushActionsChanged(it.id) }
    }

    fun getInsertEventSingle(data: InsertEventData): Single<EventId> {
        return Single
                .fromCallable { dataSource.insertEvent(data) }
                .subscribeOn(schedulers.io())
                .doOnSuccess { pushActionsChanged(data.actionId) }
    }

    fun getDeleteEventCompletable(actionId: Long, eventId: Long): Completable {
        return Completable
                .fromAction { dataSource.deleteEvent(eventId) }
                .subscribeOn(schedulers.io())
                .doOnComplete { pushActionsChanged(actionId) }
    }

    fun getDeleteActionCompletable(actionId: Long): Completable {
        return Completable
                .fromAction { dataSource.deleteAction(actionId) }
                .subscribeOn(schedulers.io())
                .doOnComplete { pushActionsChanged(actionId) }
    }

    fun getAllActionObservable(): Observable<List<Action>> {
        return actionsChangedSubject
                .observeOn(schedulers.io())
                .map { dataSource.getAllActions() }
    }

    fun getActionObservable(actionId: Long): Observable<ActionData> {
        var isFirstEvent = true
        return actionsChangedSubject
                .filter { it.actionId == actionId || isFirstEvent }
                .doOnNext { isFirstEvent = true }
                .observeOn(schedulers.io())
                .map { getActionData(actionId) }
    }

    fun getActionSingle(actionId: Long): Single<ActionData> {
        return Single
                .fromCallable { getActionData(actionId) }
    }

    private fun getActionData(actionId: Long): ActionData {
        val action = dataSource.getAction(actionId)
        return if (action == null) {
            ActionDeletedData(actionId)
        } else {
            ActionExistData(action)
        }
    }
}
