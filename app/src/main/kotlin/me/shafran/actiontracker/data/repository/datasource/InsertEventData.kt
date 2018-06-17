package me.shafran.actiontracker.data.repository.datasource

import java.util.Calendar

class InsertEventData(
        val actionId: Long,
        val value: Long,
        val trackedDate: Calendar
)
