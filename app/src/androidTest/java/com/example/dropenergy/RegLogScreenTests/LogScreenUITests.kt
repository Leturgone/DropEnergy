package com.example.dropenergy.RegLogScreenTests

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import com.example.dropenergy.LogScreen
import com.example.dropenergy.MainScreen
import com.example.dropenergy.RegScreen
import com.example.dropenergy.database.viewModel.DBViewModel
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class LogScreenUITests: KoinTest {
    val viewModel by inject<DBViewModel>()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLogScreensGood(){
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")

        //RegisterScreen
        composeTestRule.onNode(RegScreen.RegTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).performClick()
        //LoginScreen
        composeTestRule.onNode(LogScreen.LogTemplate).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).assertExists()
        composeTestRule.onNode(LogScreen.PasswordInput).assertExists()
        composeTestRule.onNode(LogScreen.NextButton).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).performTextInput("giovanni18@pochta.com")
        composeTestRule.onNode(LogScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(LogScreen.NextButton).performClick()
        composeTestRule.onRoot().printToLog("MY TAG")

    }

    @Test
    fun testLogScreensBadEmail(){

    }

    @Test
    fun testLogScreensBadPassword() {

    }

    @Test
    fun testLogScreensBadUser(){

    }

}