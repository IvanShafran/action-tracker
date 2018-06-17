package me.shafran.actiontracker.data.database.holder

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import kotlin.concurrent.withLock

class DatabaseHolderImpl @Inject constructor(
        private val actionTrackerDbHelper: SQLiteOpenHelper
) : DatabaseHolder {

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
