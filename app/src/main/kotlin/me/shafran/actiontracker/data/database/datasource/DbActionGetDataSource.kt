package me.shafran.actiontracker.data.database.datasource

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import me.shafran.actiontracker.data.database.*
import me.shafran.actiontracker.data.database.DbActionContract.Columns
import me.shafran.actiontracker.data.database.datasource.converter.ActionConverter
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.repository.ActionGetDataSource

class DbActionGetDataSource(private val databaseHolder: DatabaseHolder) : ActionGetDataSource {

    override fun getAction(actionId: Long): Action {
        databaseHolder.withDatabase { database ->
            return getAction(database, actionId)
        }
    }

    private fun getAction(database: SQLiteDatabase, actionId: Long): Action {
        val dbAction = getDbAction(database, actionId)
        val dbActionEvents = getDbActionEvents(database, actionId)

        return ActionConverter.getActionFromDb(dbAction, dbActionEvents)
    }

    private fun getDbAction(database: SQLiteDatabase, actionId: Long): DbAction {
        getDbActionCursor(database, actionId).use { cursor ->
            return getDbAction(cursor)
        }
    }

    private fun getDbAction(cursor: Cursor): DbAction {
        cursor.moveToFirst()
        return getDbActionFromCursorCurrentPosition(cursor)
    }

    private fun getDbActionFromCursorCurrentPosition(cursor: Cursor): DbAction {
        val id = cursor.getLong(cursor.getColumnIndex(Columns.ID))
        val name = cursor.getString(cursor.getColumnIndex(Columns.NAME))

        return DbAction(id, name)
    }

    private fun getDbActionCursor(database: SQLiteDatabase, actionId: Long): Cursor {
        val selection = "${Columns.ID} = ?"
        val selectionArgs = arrayOf(actionId.toString())

        return database.query(
                DbActionContract.TABLE_NAME,
                DbActionContract.FULL_PROJECTION,
                selection,
                selectionArgs,
                null,
                null,
                null
        )
    }


    override fun getAllActions(): List<Action> {
        databaseHolder.withDatabase { database ->
            return getAllActions(database)
        }
    }

    private fun getAllActions(database: SQLiteDatabase): List<Action> {
        val actions = mutableListOf<Action>()
        val dbActions = getAllDbActions(database)

        for (dbAction in dbActions) {
            val dbActionEvents = getDbActionEvents(database, dbAction.id)

            actions.add(ActionConverter.getActionFromDb(dbAction, dbActionEvents))
        }

        return actions
    }

    private fun getAllDbActions(database: SQLiteDatabase): List<DbAction> {
        getAllDbActionCursor(database).use { cursor ->
            return getAllDbActions(cursor)
        }
    }

    private fun getAllDbActions(cursor: Cursor): List<DbAction> {
        val dbActions = mutableListOf<DbAction>()

        while (cursor.moveToNext()) {
            dbActions.add(getDbActionFromCursorCurrentPosition(cursor))
        }

        return dbActions
    }

    private fun getAllDbActionCursor(database: SQLiteDatabase): Cursor {
        return database.query(
                DbActionContract.TABLE_NAME,
                DbActionContract.FULL_PROJECTION,
                null,
                null,
                null,
                null,
                null
        )
    }


    private fun getDbActionEvents(database: SQLiteDatabase, actionId: Long): List<DbActionEvent> {
        getDbActionEventsCursor(database, actionId).use { cursor ->
            return getDbActionEvents(cursor)
        }
    }

    private fun getDbActionEvents(cursor: Cursor): List<DbActionEvent> {
        val dbActionEvents = mutableListOf<DbActionEvent>()

        while (cursor.moveToNext()) {
            dbActionEvents.add(getDbActionEventFromCursor(cursor))
        }

        return dbActionEvents
    }

    private fun getDbActionEventFromCursor(cursor: Cursor): DbActionEvent {
        val id = cursor.getLong(cursor.getColumnIndex(DbActionEventContract.Columns.ID))
        val actionID = cursor.getLong(cursor.getColumnIndex(DbActionEventContract.Columns.ACTION_ID))
        val trackedDate = cursor.getLong(cursor.getColumnIndex(DbActionEventContract.Columns.TRACKED_DATE))

        return DbActionEvent(
                id = id,
                actionId = actionID,
                trackedDateSinceEpochTime = trackedDate
        )
    }

    private fun getDbActionEventsCursor(database: SQLiteDatabase, actionId: Long): Cursor {
        val selection = "${DbActionEventContract.Columns.ACTION_ID} = ?"
        return database.query(
                DbActionEventContract.TABLE_NAME,
                DbActionEventContract.FULL_PROJECTION,
                selection,
                arrayOf(actionId.toString()),
                null,
                null,
                null
        )
    }

}
