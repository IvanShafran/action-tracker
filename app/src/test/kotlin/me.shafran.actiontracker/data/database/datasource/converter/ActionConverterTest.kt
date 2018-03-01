package me.shafran.actiontracker.data.database.datasource.converter

import me.shafran.actiontracker.data.database.DbAction
import me.shafran.actiontracker.data.database.DbActionEvent
import me.shafran.actiontracker.data.entity.ActionEvent
import me.shafran.actiontracker.second
import org.hamcrest.Matchers.hasSize
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import java.util.Calendar

object ActionConverterTest : Spek({
    describe("action converter") {
        val converter = ActionConverter(ActionEventConverter(CalendarConverter()))

        on("database action without events") {
            val dbAction = DbAction(3L, "Action")

            val action = converter.getActionFromDb(dbAction, listOf())

            it("should set correct id") {
                assertEquals(dbAction.id, action.id)
            }

            it("should set correct name") {
                assertEquals(dbAction.name, action.name)
            }

            it("should not set any action events") {
                assertThat(action.events, hasSize(0))
            }
        }

        on("database action with events") {
            val dbAction = DbAction(4L, "Action with events")
            val dbEvents = listOf(
                    DbActionEvent(0, 0, 0),
                    DbActionEvent(1, 1, 1)
            )

            val action = converter.getActionFromDb(dbAction, dbEvents)

            val expectedFirstEvent = ActionEvent(
                    0,
                    Calendar.getInstance().apply { timeInMillis = 0 }
            )

            val expectedSecondEvent = ActionEvent(
                    1,
                    Calendar.getInstance().apply { timeInMillis = 1 }
            )

            it("should set correct id") {
                assertEquals(dbAction.id, action.id)
            }

            it("should set correct name") {
                assertEquals(dbAction.name, action.name)
            }

            it("should set the same number of events") {
                assertThat(action.events, hasSize(2))
            }

            it("should keep order of events") {
                assertEquals(expectedFirstEvent, action.events.first())
                assertEquals(expectedSecondEvent, action.events.second())
            }
        }
    }
})
