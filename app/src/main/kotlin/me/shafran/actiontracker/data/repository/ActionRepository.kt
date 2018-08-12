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
        private val actionDataSource: ActionDataSource
) {

    private class ChangedEvent(val actionId: Long)

    private val actionsChangedSubject: Subject<ChangedEvent> =
            BehaviorSubject.create<ChangedEvent>().toSerialized()

    init {
        pushActionsChanged(-1) // Push with fake id
    }

    private fun pushActionsChanged(actionId: Long) {
        actionsChangedSubject.onNext(ChangedEvent(actionId))
    }

    fun getInsertActionSingle(data: InsertActionData): Single<ActionId> {
        return Single
                .fromCallable { actionDataSource.insertAction(data) }
                .subscribeOn(schedulers.io())
                .doOnSuccess { pushActionsChanged(it.id) }
    }

    fun getInsertEventSingle(data: InsertEventData): Single<EventId> {
        return Single
                .fromCallable { actionDataSource.insertEvent(data) }
                .subscribeOn(schedulers.io())
                .doOnSuccess { pushActionsChanged(data.actionId) }
    }

    fun getDeleteEventCompletable(actionId: Long, eventId: Long): Completable {
        return Completable
                .fromAction { actionDataSource.deleteEvent(eventId) }
                .subscribeOn(schedulers.io())
                .doOnComplete { pushActionsChanged(actionId) }
    }

    fun getAllActionObservable(): Observable<List<Action>> {
        return actionsChangedSubject
                .observeOn(schedulers.io())
                .map { actionDataSource.getAllActions() }
    }

    fun getActionObservable(actionId: Long): Observable<Action> {
        var isFirstEvent = true
        return actionsChangedSubject
                .filter { it.actionId == actionId || isFirstEvent }
                .doOnNext { isFirstEvent = true }
                .observeOn(schedulers.io())
                .map { actionDataSource.getAction(actionId) }
    }
}
