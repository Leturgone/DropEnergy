package com.example.dropenergy

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * UI тесты
 */



@RunWith(AndroidJUnit4::class)
class UITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomBarNavigation(){
        composeTestRule.setContent { MainScreen() }
        composeTestRule.onRoot().printToLog("MY TAG")
        composeTestRule.onNode(BottomBar.bottomBarDiaryButton).performClick()
        composeTestRule.onRoot().printToLog("MY TAG")
        composeTestRule.onNode(DiaryScreen.ScreenTemplate).assertExists()

        composeTestRule.onNode((BottomBar.bottomBarStatButton)).performClick()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()

        composeTestRule.onNode(BottomBar.bottomBarAddButton).performClick()
        composeTestRule.onRoot().printToLog("MY TAG")

        composeTestRule.onNode(AddNewRecordScreen.ScreenTemlate).assertExists()
        composeTestRule.onNode(AddNewRecordScreen.NewGoodRecBtn).assertExists()
        composeTestRule.onNode(AddNewRecordScreen.NewBuyRecBtn).assertExists()
        composeTestRule.onNode(AddNewRecordScreen.NewWantRecBtn).assertExists()

    }

    @Test
    fun testProgressScreen(){
        composeTestRule.setContent { MainScreen() }
        composeTestRule.onNode(StatScreen.DaysSection).assertExists()
        composeTestRule.onNode(StatScreen.ScreenProgressTemplate).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).assertExists()
        composeTestRule.onNode(StatScreen.CanSec).assertExists().assertExists()
    }

    @Test
    fun testProgressScreenMoneyNavigation(){
        composeTestRule.setContent { MainScreen() }
        composeTestRule.onNode(StatScreen.MoneySec).performClick()
        composeTestRule.onNode(MoneyScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode((MoneyScreen.PrognozTemplate)).assertExists()
    }

    @Test
    fun testProgressScreenCansNavigation(){
        composeTestRule.setContent { MainScreen() }
        composeTestRule.onNode(StatScreen.CanSec).performClick()
        composeTestRule.onNode(CanScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode((CanScreen.PrognozTemplate)).assertExists()
    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.dropenergy", appContext.packageName)

    }
}