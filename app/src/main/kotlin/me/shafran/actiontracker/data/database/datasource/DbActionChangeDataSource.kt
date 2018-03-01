package me.shafran.actiontracker.data.database.datasource

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import me.shafran.actiontracker.data.database.DatabaseHolder
import me.shafran.actiontracker.data.database.DbActionContract
import me.shafran.actiontracker.data.database.DbActionContract.Columns
import me.shafran.actiontracker.data.database.DbActionEventContract
import me.shafran.actiontracker.data.database.datasource.converter.CalendarConverter
import me.shafran.actiontracker.data.database.withDatabase
import me.shafran.actiontracker.data.repository.ActionChangeDataSource
import me.shafran.actiontracker.data.repository.ActionChangeDataSource.InsertActionEventParams
import me.shafran.actiontracker.data.repository.ActionChangeDataSource.InsertActionParams
import me.shafran.actiontracker.data.repository.ActionChangeDataSource.UpdateActionParams

class DbActionChangeDataSource(
        private val databaseHolder: DatabaseHolder,
        private val calendarConverter: CalendarConverter
) : ActionChangeDataSource {

    override fun insertAction(insertActionParams: InsertActionParams) {
        databaseHolder.withDatabase { database ->
            insertAction(database, insertActionParams)
        }
    }

    private fun insertAction(database: SQLiteDatabase, insertActionParams: InsertActionParams) {
        val contentValues = prepareInsertActionContentValues(insertActionParams)
        database.insert(DbActionContract.TABLE_NAME, null, contentValues)
    }

    private fun prepareInsertActionContentValues(insertActionParams: InsertActionParams): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(Columns.NAME, insertActionParams.name)
        return contentValues
    }


    override fun insertActionEvent(insertActionEventParams: InsertActionEventParams) {
        databaseHolder.withDatabase { database ->
            insertActionEvent(database, insertActionEventParams)
        }
    }

    private fun insertActionEvent(database: SQLiteDatabase, insertActionEventParams: InsertActionEventParams) {
        val contentValues = prepareInsertActionEventContentValues(insertActionEventParams)
        database.insert(DbActionEventContract.TABLE_NAME, null, contentValues)
    }

    private fun prepareInsertActionEventContentValues(insertActionEventParams: InsertActionEventParams): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(DbActionEventContract.Columns.ACTION_ID, insertActionEventParams.actionId)

        val calendarLonRepresentation = calendarConverter.getLongRepresentation(insertActionEventParams.trackedDate)
        contentValues.put(DbActionEventContract.Columns.TRACKED_DATE, calendarLonRepresentation)
        return contentValues
    }


    override fun updateAction(updateActionParams: UpdateActionParams) {
        databaseHolder.withDatabase { database ->
            updateAction(database, updateActionParams)
        }
    }

    private fun updateAction(database: SQLiteDatabase, updateActionParams: UpdateActionParams) {
        val contentValues = prepareUpdateActionContentValues(updateActionParams)

        val selection = "${DbActionContract.Columns.ID} = ?"
        val selectionArgs = arrayOf(updateActionParams.actionId.toString())

        database.update(
                DbActionContract.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs
        )
    }

    private fun prepareUpdateActionContentValues(updateActionParams: UpdateActionParams): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(DbActionContract.Columns.NAME, updateActionParams.name)
        return contentValues
    }


    override fun deleteAction(actionId: Long) {
        databaseHolder.withDatabase { database ->
            deleteAction(database, actionId)
        }
    }

    private fun deleteAction(database: SQLiteDatabase, actionId: Long) {
        val selection = "${DbActionContract.Columns.ID} = ?"
        val selectionArgs = arrayOf(actionId.toString())

        database.delete(DbActionContract.TABLE_NAME, selection, selectionArgs)
    }

    override fun deleteActionEvent(actionEventId: Long) {
        databaseHolder.withDatabase { database ->
            deleteActionEvents(database, actionEventId)
        }
    }

    private fun deleteActionEvents(database: SQLiteDatabase, actionEventId: Long) {
        val selection = "${DbActionEventContract.Columns.ID} = ?"
        val selectionArgs = arrayOf(actionEventId.toString())

        database.delete(DbActionEventContract.TABLE_NAME, selection, selectionArgs)
    }

}
