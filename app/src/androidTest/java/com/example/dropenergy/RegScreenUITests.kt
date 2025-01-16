package com.example.dropenergy

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dropenergy.MainScreen
import com.example.dropenergy.RegScreen
import com.example.dropenergy.database.viewModel.DBViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject


@RunWith(AndroidJUnit4::class)
class RegScreenUITests: KoinTest {
    val viewModel by inject<DBViewModel>()

    @get:Rule
    val composeTestRule = createComposeRule()



    @Test
    fun testRegScreens(){
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")
        composeTestRule.onNode(RegScreen.RegTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).assertExists()
        composeTestRule.onNode(RegScreen.EmailInput).assertExists()
        composeTestRule.onNode(RegScreen.NextButton).assertExists()
        composeTestRule.onNode(RegScreen.PasswordInput).assertExists()
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("giovanni18@hotmail.com")
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(RegScreen.NextButton).performClick()
    }
}