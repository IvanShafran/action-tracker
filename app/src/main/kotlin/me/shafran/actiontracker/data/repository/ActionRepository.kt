package me.shafran.actiontracker.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.data.repository.datasource.EventId
import me.shafran.actiontracker.data.repository.datasource.InsertActionData
import me.shafran.actiontracker.data.repository.datasource.InsertEventData

interface ActionRepository {

    fun insertAction(data: InsertActionData): Single<ActionId>

    fun insertEvent(data: InsertEventData): Single<EventId>

    fun getAllActionObservable(): Observable<List<Action>>
}
