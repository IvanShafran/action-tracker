package me.shafran.actiontracker.data.entity

class Action(
        val id: Long,
        val name: String,
        val type: ActionType,
        val events: List<Event>
)
