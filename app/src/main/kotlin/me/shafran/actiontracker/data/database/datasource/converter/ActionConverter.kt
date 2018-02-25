package me.shafran.actiontracker.data.database.datasource.converter

import me.shafran.actiontracker.data.database.DbAction
import me.shafran.actiontracker.data.database.DbActionEvent
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.entity.ActionEvent

object ActionConverter {

    fun getActionFromDb(dbAction: DbAction, dbActionEvents: List<DbActionEvent>): Action {
        return Action(
                dbAction.id,
                dbAction.name,
                getActionEventListFromDb(dbActionEvents)
        )
    }

    private fun getActionEventListFromDb(dbActionEvents: List<DbActionEvent>): List<ActionEvent> {
        return dbActionEvents.map { getActionEventFromDb(it) }
    }

    private fun getActionEventFromDb(dbActionEvent: DbActionEvent): ActionEvent {
        val trackedDate = CalendarConverter.getCalendarFromLongRepresentation(dbActionEvent.trackedDateSinceEpochTime)
        return ActionEvent(dbActionEvent.id, trackedDate)
    }

}
