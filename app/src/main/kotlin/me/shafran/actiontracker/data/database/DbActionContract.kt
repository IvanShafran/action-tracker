package me.shafran.actiontracker.data.database

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object DbActionContract {

    object Columns {
        const val ID = BaseColumns._ID
        const val NAME = "name"
    }

    val FULL_PROJECTION = arrayOf(Columns.ID, Columns.NAME)

    const val TABLE_NAME = "actions"

    private val CREATE_TABLE_QUERY = """CREATE TABLE $TABLE_NAME (
        | ${Columns.ID} INTEGER PRIMARY KEY,
        | ${Columns.NAME} TEXT NOT NULL
        |)""".trimMargin()


    fun onCreate(database: SQLiteDatabase) {
        database.execSQL(CREATE_TABLE_QUERY)
    }

    fun

}
