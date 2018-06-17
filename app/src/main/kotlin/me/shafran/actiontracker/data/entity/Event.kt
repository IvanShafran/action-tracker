package me.shafran.actiontracker.data.entity

import java.util.Calendar

class Event(
        val id: Long,
        val actionId: Long,
        val value: Long,
        val trackedDate: Calendar
)
