package me.shafran.actiontracker.data.database.datasource

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import me.shafran.actiontracker.data.database.contracts.ActionContract
import me.shafran.actiontracker.data.database.contracts.EventContract
import me.shafran.actiontracker.data.database.converter.ActionTypeConverter
import me.shafran.actiontracker.data.database.converter.CalendarConverter
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.entity.Event

object DbGetOperations {

    fun getAction(actionId: Long, database: SQLiteDatabase): Action {
        val events = getEvents(database, actionId)
        val actionDb = getActionFromDb(actionId, database)

        return getActionWithEvents(actionDb, events)
    }

    fun getAllActions(database: SQLiteDatabase): List<Action> {
        val actions = mutableListOf<Action>()

        for (actionDb in getAllActionsFromDb(database)) {
            val events = getEvents(database, actionDb.id)

            actions.add(getActionWithEvents(actionDb, events))
        }

        return actions
    }

    private fun getActionWithEvents(action: Action, events: List<Event>): Action {
        return Action(action.id, action.name, action.type, events)
    }

    private fun getActionFromDb(actionId: Long, database: SQLiteDatabase): Action {
        getActionCursor(database, actionId).use { cursor ->
            return getActionFromDb(cursor)
        }
    }

    private fun getActionFromDb(cursor: Cursor): Action {
        cursor.moveToFirst()
        return getActionFromCursorCurrentPosition(cursor)
    }

    private fun getActionFromCursorCurrentPosition(cursor: Cursor): Action {
        val id = cursor.getLong(cursor.getColumnIndex(ActionContract.Columns.ID))
        val name = cursor.getString(cursor.getColumnIndex(ActionContract.Columns.NAME))
        val type = ActionTypeConverter.getActionTypeFromStringRepresentation(
                cursor.getString(cursor.getColumnIndex(ActionContract.Columns.TYPE))
        )

        return Action(id, name, type, listOf())
    }

    private fun getActionCursor(database: SQLiteDatabase, actionId: Long): Cursor {
        val selection = "${ActionContract.Columns.ID} = ?"
        val selectionArgs = arrayOf(actionId.toString())

        return database.query(
                ActionContract.TABLE_NAME,
                ActionContract.FULL_PROJECTION,
                selection,
                selectionArgs,
                null,
                null,
                null
        )
    }

    private fun getAllActionsFromDb(database: SQLiteDatabase): List<Action> {
        getAllActionCursor(database).use { cursor ->
            return getAllActionsFromDb(cursor)
        }
    }

    private fun getAllActionsFromDb(cursor: Cursor): List<Action> {
        val dbActions = mutableListOf<Action>()

        while (cursor.moveToNext()) {
            dbActions.add(getActionFromCursorCurrentPosition(cursor))
        }

        return dbActions
    }

    private fun getAllActionCursor(database: SQLiteDatabase): Cursor {
        return database.query(
                ActionContract.TABLE_NAME,
                ActionContract.FULL_PROJECTION,
                null,
                null,
                null,
                null,
                null
        )
    }

    private fun getEvents(database: SQLiteDatabase, actionId: Long): List<Event> {
        getEventsCursor(database, actionId).use { cursor ->
            return getEvents(cursor)
        }
    }

    private fun getEvents(cursor: Cursor): List<Event> {
        val dbActionEvents = mutableListOf<Event>()

        while (cursor.moveToNext()) {
            dbActionEvents.add(getEventFromCursor(cursor))
        }

        return dbActionEvents
    }

    private fun getEventFromCursor(cursor: Cursor): Event {
        val id = cursor.getLong(cursor.getColumnIndex(EventContract.Columns.ID))
        val actionID = cursor.getLong(cursor.getColumnIndex(EventContract.Columns.ACTION_ID))
        val value = cursor.getLong(cursor.getColumnIndex(EventContract.Columns.VALUE))
        val trackedDate = CalendarConverter.getCalendarFromLongRepresentation(
                cursor.getLong(cursor.getColumnIndex(EventContract.Columns.TRACKED_DATE))
        )

        return Event(id, actionID, value, trackedDate)
    }

    private fun getEventsCursor(database: SQLiteDatabase, actionId: Long): Cursor {
        val selection = "${EventContract.Columns.ACTION_ID} = ?"
        return database.query(
                EventContract.TABLE_NAME,
                EventContract.FULL_PROJECTION,
                selection,
                arrayOf(actionId.toString()),
                null,
                null,
                null
        )
    }
}
