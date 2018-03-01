package me.shafran.actiontracker.data.database.datasource.converter

import me.shafran.actiontracker.data.database.DbActionEvent
import me.shafran.actiontracker.data.entity.ActionEvent

class ActionEventConverter(private val calendarConverter: CalendarConverter) {

    fun getActionEventFromDb(dbActionEvent: DbActionEvent): ActionEvent {
        val trackedDate = calendarConverter.getCalendarFromLongRepresentation(dbActionEvent.trackedDateSinceEpochTime)
        return ActionEvent(dbActionEvent.id, trackedDate)
    }

}
