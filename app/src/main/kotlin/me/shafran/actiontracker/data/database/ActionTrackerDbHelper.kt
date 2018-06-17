package me.shafran.actiontracker.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import me.shafran.actiontracker.data.database.contracts.ActionContract
import me.shafran.actiontracker.data.database.contracts.EventContract
import javax.inject.Inject

class ActionTrackerDbHelper @Inject constructor(
        context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ActionTracker.db"
    }

    override fun onCreate(database: SQLiteDatabase) {
        ActionContract.createTable(database)
        EventContract.createTable(database)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        throw IllegalStateException("Database update isn't expected")
    }
}
