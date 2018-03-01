package me.shafran.actiontracker.data.database.datasource.converter

import java.util.Calendar


class CalendarConverter {

    fun getLongRepresentation(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    fun getCalendarFromLongRepresentation(longRepresentation: Long): Calendar {
        return Calendar.getInstance().apply { timeInMillis = longRepresentation }
    }

}
