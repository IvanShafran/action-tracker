package me.shafran.actiontracker.data.database.holder

import android.database.sqlite.SQLiteDatabase

interface DatabaseHolder {

    fun openDatabase(): SQLiteDatabase

    fun closeDatabase()
}

fun <T> DatabaseHolder.withDatabase(action: (database: SQLiteDatabase) -> T): T {
    val database = openDatabase()
    try {
        return action(database)
    } finally {
        closeDatabase()
    }
}
