package me.shafran.actiontracker.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class DatabaseHolderImpl(context: Context) : DatabaseHolder {

    private val actionTrackerDbHelper = ActionTrackerDbHelper(context)

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
