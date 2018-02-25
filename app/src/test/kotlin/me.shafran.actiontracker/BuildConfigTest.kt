package me.shafran.actiontracker

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert

object BuildConfigTest : Spek({
    describe("a BuildConfig") {
        on("application id") {
            val expectedApplicationId = "me.shafran.actiontracker"
            it("should be equal to \"$expectedApplicationId\"") {
                Assert.assertEquals(expectedApplicationId, BuildConfig.APPLICATION_ID)
            }
        }
    }
})
