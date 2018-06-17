package me.shafran.actiontracker.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.data.repository.datasource.InsertActionData

interface ActionRepository {

    fun insertAction(data: InsertActionData): Single<ActionId>

    fun getAllActionObservable(): Observable<List<Action>>
}
