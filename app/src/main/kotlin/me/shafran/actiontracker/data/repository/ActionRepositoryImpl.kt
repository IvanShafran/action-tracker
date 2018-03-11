package me.shafran.actiontracker.data.repository

import io.reactivex.Observable
import me.shafran.actiontracker.data.entity.Action

class ActionRepositoryImpl : ActionRepository {

    override fun getAllActionObservable(): Observable<List<Action>> {
        return Observable.just(listOf(
                Action(0, "Action", listOf())
        ))
    }
}
