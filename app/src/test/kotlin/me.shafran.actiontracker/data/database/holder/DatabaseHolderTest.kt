package me.shafran.actiontracker.data.database.holder

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import me.shafran.actiontracker.assertFailsWith
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

object DatabaseHolderTest : Spek({

    var databaseHelper: SQLiteOpenHelper = mock()
    var databaseMock: SQLiteDatabase = mock()
    var holder: DatabaseHolder = mock()

    fun prepareMocks() {
        databaseHelper = mock()
        databaseMock = mock()
        whenever(databaseHelper.writableDatabase).thenReturn(databaseMock)
        holder = DatabaseHolderImpl(databaseHelper)
    }

    describe("database holder") {
        on("first open call") {
            prepareMocks()

            val database = holder.openDatabase()

            it("should return writable database") {
                assertEquals(databaseMock, database)
            }

            it("should open database internally") {
                verify(databaseHelper).writableDatabase
            }
        }

        on("two open calls in a row") {
            prepareMocks()

            holder.openDatabase()
            holder.openDatabase()

            it("should open database internally only once") {
                verify(databaseHelper).writableDatabase
            }
        }

        on("open and then close calls") {
            prepareMocks()

            holder.openDatabase()
            holder.closeDatabase()

            it("should open database internally") {
                verify(databaseHelper).writableDatabase
            }

            it("should close database internally") {
                verify(databaseMock).close()
            }
        }

        on("open, open, close, close calls") {
            prepareMocks()

            holder.openDatabase()
            holder.openDatabase()
            holder.closeDatabase()
            holder.closeDatabase()

            it("should open database internally once") {
                verify(databaseHelper).writableDatabase
            }

            it("should close database internally once") {
                verify(databaseMock).close()
            }
        }

        on("open, close, open, close calls") {
            prepareMocks()

            holder.openDatabase()
            holder.closeDatabase()
            holder.openDatabase()
            holder.closeDatabase()

            it("should open database internally twice") {
                verify(databaseHelper, times(2)).writableDatabase
            }

            it("should close database internally twice") {
                verify(databaseMock, times(2)).close()
            }
        }

        on("close without open") {
            prepareMocks()

            it("should throw illegal state exception") {
                assertFailsWith<IllegalStateException> {
                    holder.closeDatabase()
                }
            }
        }
    }
})
