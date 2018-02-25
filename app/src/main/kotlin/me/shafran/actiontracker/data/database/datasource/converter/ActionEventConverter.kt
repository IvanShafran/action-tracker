package me.shafran.actiontracker.data.database.datasource.converter

import me.shafran.actiontracker.data.database.DbActionEvent
import me.shafran.actiontracker.data.entity.ActionEvent

object ActionEventConverter {

    fun getActionEventFromDb(dbActionEvent: DbActionEvent): ActionEvent {
        val trackedDate = CalendarConverter.getCalendarFromLongRepresentation(dbActionEvent.trackedDateSinceEpochTime)
        return ActionEvent(dbActionEvent.id, trackedDate)
    }

}
