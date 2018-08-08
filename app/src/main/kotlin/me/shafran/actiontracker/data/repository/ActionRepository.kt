package me.shafran.actiontracker.data.repository

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

    private class ChangedEvent

    private val actionsChangedSubject: Subject<ChangedEvent> =
            BehaviorSubject.create<ChangedEvent>().toSerialized()

    init {
        pushActionsChanged()
    }

    private fun pushActionsChanged() {
        actionsChangedSubject.onNext(ChangedEvent())
    }

    fun insertAction(data: InsertActionData): Single<ActionId> {
        return Single
                .fromCallable { actionDataSource.insertAction(data) }
                .subscribeOn(schedulers.io())
                .doOnSuccess { pushActionsChanged() }
    }

    fun insertEvent(data: InsertEventData): Single<EventId> {
        return Single
                .fromCallable { actionDataSource.insertEvent(data) }
                .subscribeOn(schedulers.io())
                .doOnSuccess { pushActionsChanged() }
    }

    fun getAllActionObservable(): Observable<List<Action>> {
        return actionsChangedSubject
                .observeOn(schedulers.io())
                .map { actionDataSource.getAllActions() }
    }
}
