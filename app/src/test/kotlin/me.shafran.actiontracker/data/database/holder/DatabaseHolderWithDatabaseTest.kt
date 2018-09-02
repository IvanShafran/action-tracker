package me.shafran.actiontracker.data.database.holder

import android.database.sqlite.SQLiteDatabase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import me.shafran.actiontracker.assertFailsWith
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object DatabaseHolderWithDatabaseTest : Spek({

    describe("with database extension") {
        context("on action") {
            val databaseHolder: DatabaseHolder = mock()
            whenever(databaseHolder.openDatabase()).thenReturn(mock())
            val action: (database: SQLiteDatabase) -> Unit = mock()
            databaseHolder.withDatabase(action)

            it("should open database") {
                verify(databaseHolder).openDatabase()
            }

            it("should call action") {
                verify(action).invoke(any())
            }

            it("should close database") {
                verify(databaseHolder).closeDatabase()
            }
        }

        context("on action that throws exception") {
            val databaseHolder: DatabaseHolder = mock()
            whenever(databaseHolder.openDatabase()).thenReturn(mock())
            val action: (database: SQLiteDatabase) -> Unit = { throw IllegalStateException() }

            it("should close exception and close database") {
                assertFailsWith<IllegalStateException> {
                    databaseHolder.withDatabase(action)
                }
                verify(databaseHolder).closeDatabase()
            }
        }
    }
})
