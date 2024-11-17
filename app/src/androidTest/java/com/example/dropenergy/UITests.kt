package com.example.dropenergy

import androidx.compose.ui.test.junit4.createComposeRule
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
    fun testAddRecBuydNavigation(){
        composeTestRule.setContent { MainScreen() }
        composeTestRule.onNode(BottomBar.bottomBarAddButton).performClick()
        composeTestRule.onNode(AddNewRecordScreen.NewBuyRecBtn).performClick()
        composeTestRule.onRoot().printToLog("MY TAG")

        composeTestRule.onNode(NewRecordScreen.ScreenTemplateBuy).assertExists()
        composeTestRule.onNode(NewRecordScreen.SaveButton).performClick()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
    }

    @Test
    fun testAddRecWantdNavigation(){
        composeTestRule.setContent { MainScreen() }
        composeTestRule.onNode(BottomBar.bottomBarAddButton).performClick()
        composeTestRule.onNode(AddNewRecordScreen.NewWantRecBtn).performClick()
        composeTestRule.onRoot().printToLog("MY TAG")

        composeTestRule.onNode(NewRecordScreen.ScreenTemplateWant).assertExists()
        composeTestRule.onNode(NewRecordScreen.SaveButton).performClick()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
    }

    @Test
    fun testAddRecGooddNavigation(){
        composeTestRule.setContent { MainScreen() }
        composeTestRule.onNode(BottomBar.bottomBarAddButton).performClick()
        composeTestRule.onNode(AddNewRecordScreen.NewGoodRecBtn).performClick()
        composeTestRule.onRoot().printToLog("MY TAG")

        composeTestRule.onNode(NewRecordScreen.ScreenTemplateGood).assertExists()
        composeTestRule.onNode(NewRecordScreen.SaveButton).performClick()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
    }


    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.dropenergy", appContext.packageName)

    }
}