package me.shafran.actiontracker.data.repository.datasource

import me.shafran.actiontracker.data.entity.Action

interface ActionDataSource {

    /**
     * @return inserted action id
     */
    fun insertAction(data: InsertActionData): ActionId

    /**
     * @return inserted event id
     */
    fun insertEvent(data: InsertEventData): EventId

    fun updateAction(data: UpdateActionData)

    fun deleteAction(actionId: Long)

    fun deleteEvent(eventId: Long)

    fun getAction(actionId: Long): Action?

    fun getAllActions(): List<Action>
}
