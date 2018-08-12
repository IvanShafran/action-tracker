package me.shafran.actiontracker.data.database.datasource

import me.shafran.actiontracker.data.database.holder.DatabaseHolder
import me.shafran.actiontracker.data.database.holder.withDatabase
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.repository.datasource.ActionDataSource
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.data.repository.datasource.EventId
import me.shafran.actiontracker.data.repository.datasource.InsertActionData
import me.shafran.actiontracker.data.repository.datasource.InsertEventData
import me.shafran.actiontracker.data.repository.datasource.UpdateActionData
import javax.inject.Inject

class DbActionDataSource @Inject constructor(
        private val databaseHolder: DatabaseHolder
) : ActionDataSource {

    override fun insertAction(data: InsertActionData): ActionId {
        return databaseHolder.withDatabase { database -> DbChangeOperations.insertAction(data, database) }
    }

    override fun insertEvent(data: InsertEventData): EventId {
        return databaseHolder.withDatabase { database -> DbChangeOperations.insertEvent(data, database) }
    }

    override fun updateAction(data: UpdateActionData) {
        databaseHolder.withDatabase { database -> DbChangeOperations.updateAction(data, database) }
    }

    override fun deleteAction(actionId: Long) {
        databaseHolder.withDatabase { database -> DbChangeOperations.deleteAction(actionId, database) }
    }

    override fun deleteEvent(eventId: Long) {
        databaseHolder.withDatabase { database -> DbChangeOperations.deleteEvent(eventId, database) }
    }

    override fun getAction(actionId: Long): Action? {
        return databaseHolder.withDatabase { database -> DbGetOperations.getAction(actionId, database) }
    }

    override fun getAllActions(): List<Action> {
        return databaseHolder.withDatabase { database -> DbGetOperations.getAllActions(database) }
    }
}
