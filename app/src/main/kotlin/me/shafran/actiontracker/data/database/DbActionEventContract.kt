package me.shafran.actiontracker.data.database

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object DbActionEventContract {

    object Columns {
        const val ID = BaseColumns._ID
        const val ACTION_ID = "action_id"
        const val TRACKED_DATE = "tracked_date"
    }

    val FULL_PROJECTION = arrayOf(Columns.ID, Columns.ACTION_ID, Columns.TRACKED_DATE)

    const val TABLE_NAME = "action_events"

    private val CREATE_TABLE_QUERY = """CREATE TABLE $TABLE_NAME (
        | ${Columns.ID} INTEGER PRIMARY KEY,
        | ${Columns.ACTION_ID} INTEGER NOT NULL,
        | ${Columns.TRACKED_DATE} INTEGER NOT NULL
        |)""".trimMargin()


    fun onCreate(database: SQLiteDatabase) {
        database.execSQL(CREATE_TABLE_QUERY)
    }


}
