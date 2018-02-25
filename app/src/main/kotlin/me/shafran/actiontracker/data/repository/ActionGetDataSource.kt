package me.shafran.actiontracker.data.repository

import me.shafran.actiontracker.data.entity.Action

interface ActionGetDataSource {

    fun getAction(actionId: Long): Action

    fun getAllActions(): List<Action>

}
