package me.shafran.actiontracker.data.database.converter

import java.util.Calendar

object CalendarConverter {

    fun getLongRepresentation(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    fun getCalendarFromLongRepresentation(longRepresentation: Long): Calendar {
        return Calendar.getInstance().apply { timeInMillis = longRepresentation }
    }
}
