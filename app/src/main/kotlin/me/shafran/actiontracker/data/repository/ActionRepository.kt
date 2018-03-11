package me.shafran.actiontracker.data.repository

import io.reactivex.Observable
import me.shafran.actiontracker.data.entity.Action

interface ActionRepository {

    fun getAllActionObservable(): Observable<List<Action>>

}
