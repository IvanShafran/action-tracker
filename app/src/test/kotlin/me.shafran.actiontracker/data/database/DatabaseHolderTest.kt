package me.shafran.actiontracker.data.database

import android.database.sqlite.SQLiteDatabase
import me.shafran.actiontracker.assertFailsWith
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.times

object DatabaseHolderTest : Spek({

    describe("database holder") {
        on("first open call") {
            val databaseHelper = Mockito.mock(ActionTrackerDbHelper::class.java)
            val databaseMock = Mockito.mock(SQLiteDatabase::class.java)
            Mockito.`when`(databaseHelper.writableDatabase).thenReturn(databaseMock)

            val holder = DatabaseHolderImpl(databaseHelper)

            val database = holder.openDatabase()

            it("should return writable database") {
                assertEquals(databaseMock, database)
            }

            it("should open database internally") {
                Mockito.verify(databaseHelper).writableDatabase
            }
        }

        on("several open calls") {
            val databaseHelper = Mockito.mock(ActionTrackerDbHelper::class.java)
            val databaseMock = Mockito.mock(SQLiteDatabase::class.java)
            Mockito.`when`(databaseHelper.writableDatabase).thenReturn(databaseMock)
            val holder = DatabaseHolderImpl(databaseHelper)

            holder.openDatabase()
            holder.openDatabase()
            holder.openDatabase()

            it("should open database internally only once") {
                Mockito.verify(databaseHelper).writableDatabase
            }
        }

        on("open and then close calls") {
            val databaseHelper = Mockito.mock(ActionTrackerDbHelper::class.java)
            val databaseMock = Mockito.mock(SQLiteDatabase::class.java)
            Mockito.`when`(databaseHelper.writableDatabase).thenReturn(databaseMock)
            val holder = DatabaseHolderImpl(databaseHelper)

            holder.openDatabase()
            holder.closeDatabase()

            it("should open database internally") {
                Mockito.verify(databaseHelper).writableDatabase
            }

            it("should close database internally") {
                Mockito.verify(databaseMock).close()
            }
        }

        on("open, open, close, close calls") {
            val databaseHelper = Mockito.mock(ActionTrackerDbHelper::class.java)
            val databaseMock = Mockito.mock(SQLiteDatabase::class.java)
            Mockito.`when`(databaseHelper.writableDatabase).thenReturn(databaseMock)
            val holder = DatabaseHolderImpl(databaseHelper)

            holder.openDatabase()
            holder.openDatabase()
            holder.closeDatabase()
            holder.closeDatabase()

            it("should open database internally once") {
                Mockito.verify(databaseHelper).writableDatabase
            }

            it("should close database internally once") {
                Mockito.verify(databaseMock).close()
            }
        }

        on("open, close, open, close calls") {
            val databaseHelper = Mockito.mock(ActionTrackerDbHelper::class.java)
            val databaseMock = Mockito.mock(SQLiteDatabase::class.java)
            Mockito.`when`(databaseHelper.writableDatabase).thenReturn(databaseMock)
            val holder = DatabaseHolderImpl(databaseHelper)

            holder.openDatabase()
            holder.closeDatabase()
            holder.openDatabase()
            holder.closeDatabase()

            it("should open database internally twice") {
                Mockito.verify(databaseHelper, times(2)).writableDatabase
            }

            it("should close database internally twice") {
                Mockito.verify(databaseMock, times(2)).close()
            }
        }

        on("close without open") {
            val databaseHelper = Mockito.mock(ActionTrackerDbHelper::class.java)
            val databaseMock = Mockito.mock(SQLiteDatabase::class.java)
            Mockito.`when`(databaseHelper.writableDatabase).thenReturn(databaseMock)
            val holder = DatabaseHolderImpl(databaseHelper)

            it("should throw illegal state exception") {
                assertFailsWith<IllegalStateException> {
                    holder.closeDatabase()
                }
            }
        }
    }
})
