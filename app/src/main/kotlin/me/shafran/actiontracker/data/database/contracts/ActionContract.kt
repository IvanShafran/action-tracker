package me.shafran.actiontracker.data.database.contracts

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object ActionContract {

    object Columns {
        const val ID = BaseColumns._ID
        const val NAME = "NAME"
        const val TYPE = "TYPE"
    }

    val FULL_PROJECTION = arrayOf(
            Columns.ID,
            Columns.NAME,
            Columns.TYPE
    )

    const val TABLE_NAME = "actions"

    private val CREATE_TABLE_QUERY = """CREATE TABLE $TABLE_NAME (
        | ${Columns.ID} INTEGER PRIMARY KEY,
        | ${Columns.NAME} TEXT NOT NULL,
        | ${Columns.TYPE} TEXT NOT NULL
        |)""".trimMargin()

    fun createTable(database: SQLiteDatabase) {
        database.execSQL(CREATE_TABLE_QUERY)
    }
}
