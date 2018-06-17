package me.shafran.actiontracker.data.database.datasource

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import me.shafran.actiontracker.data.database.contracts.ActionContract
import me.shafran.actiontracker.data.database.contracts.EventContract
import me.shafran.actiontracker.data.database.converter.ActionTypeConverter
import me.shafran.actiontracker.data.database.converter.CalendarConverter
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.data.repository.datasource.EventId
import me.shafran.actiontracker.data.repository.datasource.InsertActionData
import me.shafran.actiontracker.data.repository.datasource.InsertEventData
import me.shafran.actiontracker.data.repository.datasource.UpdateActionData

object DbChangeOperations {

    fun insertAction(data: InsertActionData, database: SQLiteDatabase): ActionId {
        val contentValues = prepareInsertActionContentValues(data)
        val id = database.insert(ActionContract.TABLE_NAME, null, contentValues)
        return ActionId(id)
    }

    private fun prepareInsertActionContentValues(data: InsertActionData): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(ActionContract.Columns.NAME, data.name)

        val typeValue: String = ActionTypeConverter.getStringRepresentation(data.type)
        contentValues.put(ActionContract.Columns.TYPE, typeValue)
        return contentValues
    }

    fun insertEvent(data: InsertEventData, database: SQLiteDatabase): EventId {
        val contentValues = prepareInsertEventContentValues(data)
        val id = database.insert(EventContract.TABLE_NAME, null, contentValues)
        return EventId(id)
    }

    private fun prepareInsertEventContentValues(data: InsertEventData): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(EventContract.Columns.ACTION_ID, data.actionId)

        val calendarLonRepresentation = CalendarConverter.getLongRepresentation(data.trackedDate)
        contentValues.put(EventContract.Columns.TRACKED_DATE, calendarLonRepresentation)
        return contentValues
    }

    fun updateAction(data: UpdateActionData, database: SQLiteDatabase) {
        val contentValues = prepareUpdateActionContentValues(data)

        val selection = "${ActionContract.Columns.ID} = ?"
        val selectionArgs = arrayOf(data.id.toString())

        database.update(
                ActionContract.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs
        )
    }

    private fun prepareUpdateActionContentValues(data: UpdateActionData): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(ActionContract.Columns.NAME, data.name)
        return contentValues
    }

    fun deleteAction(actionId: Long, database: SQLiteDatabase) {
        val selection = "${ActionContract.Columns.ID} = ?"
        val selectionArgs = arrayOf(actionId.toString())

        database.delete(ActionContract.TABLE_NAME, selection, selectionArgs)
    }

    fun deleteEvent(eventId: Long, database: SQLiteDatabase) {
        val selection = "${EventContract.Columns.ID} = ?"
        val selectionArgs = arrayOf(eventId.toString())

        database.delete(EventContract.TABLE_NAME, selection, selectionArgs)
    }
}
