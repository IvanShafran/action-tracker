package me.shafran.actiontracker.data.repository.datasource

import me.shafran.actiontracker.data.entity.Action

interface ActionDataSource {

    /**
     * @return inserted action id
     * @throws ActionDataSourceException
     */
    fun insertAction(data: InsertActionData): ActionId

    /**
     * @return inserted event id
     * @throws ActionDataSourceException
     */
    fun insertEvent(data: InsertEventData): EventId

    /**
     * @throws ActionDataSourceException
     */
    fun updateAction(data: UpdateActionData)

    /**
     * @throws ActionDataSourceException
     */
    fun deleteAction(actionId: Long)

    /**
     * @throws ActionDataSourceException
     */
    fun deleteEvent(eventId: Long)

    /**
     * @throws ActionDataSourceException
     */
    fun getAction(actionId: Long): Action

    /**
     * @throws ActionDataSourceException
     */
    fun getAllActions(): List<Action>
}
