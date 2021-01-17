package com.experiment.android.spacexpastlaunchtracker.utils.extensions

import com.experiment.android.spacexpastlaunchtracker.utils.constants.Constants
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExtensionsTest {
    @Test
    fun testAppendFunction() {
        assertEquals(
            "Rocket Name , Rocket Type",
            "Rocket Name".append { " , " }.append { "Rocket Type" })
        assertEquals("HelloWorld", "Hello".append { "World" })
        assertEquals("Test String", "Test".append { " String" })
    }

    @Test
    fun testToDateFunction() {
        val testDate = "2020-11-21T17:17:00.000Z"
        val expectedDate = "Sat Nov 21 22:47:00 IST 2020"

        assertEquals(expectedDate, testDate.toDate().toString())
    }

    @Test
    fun testFormatToFunction() {
        val testDate = "2020-10-24T15:31:00.000Z"
        val expectedDate = "Sat, 24 Oct 2020 21:01:00"

        assertEquals(expectedDate, testDate.toDate().formatTo(Constants.DISPLAY_DATE_FORMAT))
    }

}