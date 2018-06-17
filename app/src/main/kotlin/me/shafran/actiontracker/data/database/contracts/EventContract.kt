package me.shafran.actiontracker.data.database.contracts

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object EventContract {

    object Columns {
        const val ID = BaseColumns._ID
        const val ACTION_ID = "ACTION_ID"
        const val VALUE = "VALUE"
        const val TRACKED_DATE = "TRACKED_DATE"
    }

    val FULL_PROJECTION = arrayOf(
            Columns.ID,
            Columns.ACTION_ID,
            Columns.VALUE,
            Columns.TRACKED_DATE
    )

    const val TABLE_NAME = "events"

    private val CREATE_TABLE_QUERY = """CREATE TABLE $TABLE_NAME (
        | ${Columns.ID} INTEGER PRIMARY KEY,
        | ${Columns.ACTION_ID} INTEGER NOT NULL,
        | ${Columns.VALUE} INTEGER NOT NULL,
        | ${Columns.TRACKED_DATE} INTEGER NOT NULL
        |)""".trimMargin()

    fun createTable(database: SQLiteDatabase) {
        database.execSQL(CREATE_TABLE_QUERY)
    }
}
