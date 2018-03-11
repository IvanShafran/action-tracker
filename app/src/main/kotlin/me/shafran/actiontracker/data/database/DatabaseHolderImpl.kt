package me.shafran.actiontracker.data.database

import android.database.sqlite.SQLiteDatabase
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class DatabaseHolderImpl(private val actionTrackerDbHelper: ActionTrackerDbHelper) : DatabaseHolder {

    private lateinit var sqLiteDatabase: SQLiteDatabase

    private val databaseOpenCloseBalance = AtomicInteger()

    private val reentrantLock = ReentrantLock()

    override fun openDatabase() = reentrantLock.withLock {
        if (databaseOpenCloseBalance.incrementAndGet() == 1) {
            sqLiteDatabase = actionTrackerDbHelper.writableDatabase
        }

        return@withLock sqLiteDatabase
    }

    override fun closeDatabase() = reentrantLock.withLock {
        val balanceValue = databaseOpenCloseBalance.decrementAndGet()

        if (balanceValue < 0) {
            throw IllegalStateException("Database is closed more times than opened")
        }

        if (balanceValue == 0) {
            sqLiteDatabase.close()
        }
    }
}
