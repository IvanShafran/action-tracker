package me.shafran.actiontracker.data.database.datasource.converter

import me.shafran.actiontracker.data.database.DbAction
import me.shafran.actiontracker.data.database.DbActionEvent
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.entity.ActionEvent

class ActionConverter(private val actionEventConverter: ActionEventConverter) {

    fun getActionFromDb(dbAction: DbAction, dbActionEvents: List<DbActionEvent>): Action {
        return Action(
                dbAction.id,
                dbAction.name,
                getActionEventListFromDb(dbActionEvents)
        )
    }

    private fun getActionEventListFromDb(dbActionEvents: List<DbActionEvent>): List<ActionEvent> {
        return dbActionEvents.map { actionEventConverter.getActionEventFromDb(it) }
    }

}
