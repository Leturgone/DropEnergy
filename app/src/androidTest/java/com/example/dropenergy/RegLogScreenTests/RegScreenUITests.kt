package com.example.dropenergy.RegLogScreenTests

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dropenergy.DCansScreen
import com.example.dropenergy.DMoneyScreen
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
    fun testRegScreensGood(){
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")

        //RegisterScreen
        composeTestRule.onNode(RegScreen.RegTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).assertExists()
        composeTestRule.onNode(RegScreen.EmailInput).assertExists()
        composeTestRule.onNode(RegScreen.NextButton).assertExists()
        composeTestRule.onNode(RegScreen.PasswordInput).assertExists()
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("giovanni18@pochta.com")
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(RegScreen.NextButton).performClick()

        //DialogCansScreen
        composeTestRule.onNode(DCansScreen.regCansTemplate).assertExists()
        composeTestRule.onNode(DCansScreen.countInput).assertExists()
        composeTestRule.onNode(DCansScreen.nextButton).assertExists()
        composeTestRule.onNode(DCansScreen.countInput).performTextInput("2")
        composeTestRule.onNode(DCansScreen.nextButton).performClick()

        //DialogMoneyScreen
        composeTestRule.onNode(DMoneyScreen.regMoneyTemplate).assertExists()
        composeTestRule.onNode(DMoneyScreen.priceInput).assertExists()
        composeTestRule.onNode(DMoneyScreen.currencyButton).assertExists()
        composeTestRule.onNode(DMoneyScreen.nextButton).assertExists()
        composeTestRule.onNode(DMoneyScreen.priceInput).performTextInput("60")
        composeTestRule.onNode(DMoneyScreen.nextButton).performClick()

    }

    @Test
    fun testRegScreenBadEmail(){
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("ghgfhjgdhfgdhgj")
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(RegScreen.NextButton).performClick()

    }

    @Test
    fun testRegScreenBadPassword() {

    }

    @Test
    fun testRegScreenBadCans(){

    }

    @Test
    fun testRegScreenBadMoney(){

    }

    @Test
    fun testRegScreenAlreadyUser(){

    }

}