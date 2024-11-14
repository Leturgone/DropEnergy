package com.example.dropenergy

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * UI тесты
 */

@get:Rule
val composeTestRule = createAndroidComposeRule<MainActivity>()

@RunWith(AndroidJUnit4::class)
class UITests {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.dropenergy", appContext.packageName)
    }
}