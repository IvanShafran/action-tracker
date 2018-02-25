package me.shafran.actiontracker.data.entity

import java.util.Calendar

data class ActionEvent(val id: Long, val trackedDate: Calendar)

data class Action(
        val id: Long,
        val name: String,
        val events: List<ActionEvent>
)
