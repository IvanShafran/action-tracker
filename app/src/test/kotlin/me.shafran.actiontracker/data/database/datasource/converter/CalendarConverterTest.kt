package me.shafran.actiontracker.data.database.datasource.converter

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import java.util.Calendar

object CalendarConverterTest : Spek({
    describe("calendar converter") {
        val calendarConverter = CalendarConverter()

        on("converting from long representation to calendar and back") {
            val longRepresentation = 1323223898L
            val returnedLonRepresentation = calendarConverter.getLongRepresentation(
                    calendarConverter.getCalendarFromLongRepresentation(longRepresentation)
            )

            it("should return the same long") {
                assertEquals(longRepresentation, returnedLonRepresentation)
            }
        }

        on("converting from calendar to long representation and back") {
            val calendar = Calendar.getInstance().apply { timeInMillis = 3723832238L }
            val returnedCalendar = calendarConverter.getCalendarFromLongRepresentation(
                    calendarConverter.getLongRepresentation(calendar)
            )

            it("should return the same long") {
                assertEquals(calendar, returnedCalendar)
            }
        }
    }
})
