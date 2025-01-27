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
import com.example.dropenergy.StatScreen
import com.example.dropenergy.database.repository.AuthRepository
import com.example.dropenergy.database.repository.GetDBState
import com.example.dropenergy.database.repository.UserRepository
import com.example.dropenergy.database.viewModel.DBViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject


@RunWith(AndroidJUnit4::class)
class RegScreenUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val authRepository = mockk<AuthRepository>()
    private val userRepository = mockk<UserRepository>()
    private lateinit var viewModel: DBViewModel

    @Before
    fun setUP(){
        coEvery {authRepository.signup("giovanni18@pochta.com",
            "12345678")
        } returns GetDBState.Success(mockk())
        coEvery {authRepository.signup("1 1 1", "12345678")
        } returns GetDBState.Failure(mockk())
        coEvery {authRepository.signup("giovanni18@pochta.com", "1")
        } returns GetDBState.Failure(mockk())
        coEvery {authRepository.signup("giovanni18@pochta.com", "1         ")
        } returns GetDBState.Failure(mockk())
        every { authRepository.getCurrentUser() } returns null
        coEvery {authRepository.signup("logged@pochta.com", "12345678")
        } returns GetDBState.Failure(mockk())
        viewModel = DBViewModel(authRepository, userRepository)
    }

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
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()

    }

    @Test
    fun testRegScreenBadEmail(){
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("1 1 1")
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(RegScreen.NextButton).performClick()
        composeTestRule.onNode(RegScreen.EmailErrorToast).assertExists()
        composeTestRule.onNode(DCansScreen.regCansTemplate).assertDoesNotExist()

    }

    @Test
    fun testRegScreenBadPassword() {
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("giovanni18@pochta.com")
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("1         ")
        composeTestRule.onNode(RegScreen.NextButton).performClick()
        composeTestRule.onNode(RegScreen.PasswordErrorToast).assertExists()
        composeTestRule.onNode(DCansScreen.regCansTemplate).assertDoesNotExist()
    }
    @Test
    fun testRegScreenShortPassword() {
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("giovanni18@pochta.com")
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("1")
        composeTestRule.onNode(RegScreen.NextButton).performClick()
        composeTestRule.onNode(RegScreen.ShortPasswordErrorToast).assertExists()
        composeTestRule.onNode(DCansScreen.regCansTemplate).assertDoesNotExist()
    }

    @Test
    fun testRegScreenBadCans(){
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("giovanni18@pochta.com")
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(RegScreen.NextButton).performClick()

        //DialogCansScreen
        composeTestRule.onNode(DCansScreen.regCansTemplate).assertExists()
        composeTestRule.onNode(DCansScreen.countInput).assertExists()
        composeTestRule.onNode(DCansScreen.nextButton).assertExists()
        composeTestRule.onNode(DCansScreen.countInput).performTextInput("2   ")
        composeTestRule.onNode(DCansScreen.nextButton).performClick()
        composeTestRule.onNode(DCansScreen.NumberError).assertExists()
        composeTestRule.onNode(DMoneyScreen.regMoneyTemplate).assertDoesNotExist()

    }

    @Test
    fun testRegScreenBadMoney(){
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")

        //RegisterScreen
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("logged@pochta.com",)
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(RegScreen.NextButton).performClick()

        //DialogCansScreen
        composeTestRule.onNode(DCansScreen.countInput).performTextInput("2")
        composeTestRule.onNode(DCansScreen.nextButton).performClick()

        //DialogMoneyScreen
        composeTestRule.onNode(DMoneyScreen.priceInput).performTextInput("...  ")
        composeTestRule.onNode(DMoneyScreen.nextButton).performClick()
        composeTestRule.onNode(DMoneyScreen.numberError).assertExists()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertDoesNotExist()
    }

    @Test
    fun testRegScreenAlreadyUser(){
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onRoot().printToLog("MY TAG")

        //RegisterScreen
        composeTestRule.onNode(RegScreen.EmailInput).performTextInput("logged@pochta.com",)
        composeTestRule.onNode(RegScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(RegScreen.NextButton).performClick()

        //DialogCansScreen
        composeTestRule.onNode(DCansScreen.countInput).performTextInput("2")
        composeTestRule.onNode(DCansScreen.nextButton).performClick()

        //DialogMoneyScreen
        composeTestRule.onNode(DMoneyScreen.priceInput).performTextInput("60")
        composeTestRule.onNode(DMoneyScreen.nextButton).performClick()
        composeTestRule.onNode(DMoneyScreen.signupError).assertExists()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertDoesNotExist()

    }


}