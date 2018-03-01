package me.shafran.actiontracker.data.database.datasource.converter

import me.shafran.actiontracker.data.database.DbActionEvent
import me.shafran.actiontracker.data.entity.ActionEvent
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals

object ActionEventConverterTest : Spek({
    describe("action event converter") {
        val calendarConverter = CalendarConverter()
        val eventConverter = ActionEventConverter(calendarConverter)

        val dbActionEvent = DbActionEvent(
                id = 1,
                actionId = 2,
                trackedDateSinceEpochTime = 0
        )

        val actionEvent = eventConverter.getActionEventFromDb(dbActionEvent)

        val expectedActionEvent = ActionEvent(
                id = 1,
                trackedDate = calendarConverter.getCalendarFromLongRepresentation(0)
        )

        it("should set correct id") {
            assertEquals(expectedActionEvent.id, actionEvent.id)
        }

        it("should set correct tracked time") {
            assertEquals(expectedActionEvent.trackedDate, actionEvent.trackedDate)
        }
    }
})
