package com.example.dropenergy.RegLogScreenTests

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import com.example.dropenergy.LogScreen
import com.example.dropenergy.MainScreen
import com.example.dropenergy.RegScreen
import com.example.dropenergy.StatScreen
import com.example.dropenergy.database.repository.AuthRepository
import com.example.dropenergy.database.repository.GetDBState
import com.example.dropenergy.database.repository.UserRepository
import com.example.dropenergy.database.viewModel.DBViewModel
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LogScreenUITests{
    @get:Rule
    val composeTestRule = createComposeRule()
    private val authRepository = mockk<AuthRepository>()
    private val userRepository = mockk<UserRepository>()
    private lateinit var viewModel: DBViewModel

    @Before
    fun setUP(){
        coEvery {authRepository.login("giovanni18@pochta.com", "12345678")
        } returns GetDBState.Success(mockk())
        coEvery {authRepository.login("1 1 1", "12345678")
        } returns GetDBState.Failure(mockk())
        coEvery {authRepository.login("giovanni18@pochta.com", "1")
        } returns GetDBState.Failure(mockk())
        coEvery {authRepository.login("giovanni18@pochta.com", "1         ")
        } returns GetDBState.Failure(mockk())
        every { authRepository.getCurrentUser() } returns null
        coEvery {authRepository.login("notlogged@pochta.com", "12345678")
        } returns GetDBState.Failure(mockk())
        viewModel = DBViewModel(authRepository, userRepository)
    }

    @Test
    fun testLogScreensGood(){

        composeTestRule.setContent { MainScreen(viewModel) }

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
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
    }

    @Test
    fun testLogScreensBadEmail(){
        composeTestRule.setContent { MainScreen(viewModel) }

        composeTestRule.onNode(RegScreen.RegTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).performClick()
        //LoginScreen
        composeTestRule.onNode(LogScreen.LogTemplate).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).assertExists()
        composeTestRule.onNode(LogScreen.PasswordInput).assertExists()
        composeTestRule.onNode(LogScreen.NextButton).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).performTextInput("1 1 1")
        composeTestRule.onNode(LogScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(LogScreen.NextButton).performClick()
        composeTestRule.onNode(LogScreen.EmailErrorToast).assertExists()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertDoesNotExist()

    }

    @Test
    fun testLogScreensShortPassword() {
        composeTestRule.setContent { MainScreen(viewModel) }

        composeTestRule.onNode(RegScreen.RegTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).performClick()
        //LoginScreen
        composeTestRule.onNode(LogScreen.LogTemplate).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).assertExists()
        composeTestRule.onNode(LogScreen.PasswordInput).assertExists()
        composeTestRule.onNode(LogScreen.NextButton).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).performTextInput("giovanni18@pochta.com")
        composeTestRule.onNode(LogScreen.PasswordInput).performTextInput("1")
        composeTestRule.onNode(LogScreen.NextButton).performClick()
        composeTestRule.onNode(LogScreen.ShortPasswordErrorToast).assertExists()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertDoesNotExist()
    }

    @Test
    fun testLogScreensBadPassword() {
        composeTestRule.setContent { MainScreen(viewModel) }

        composeTestRule.onNode(RegScreen.RegTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).performClick()
        //LoginScreen
        composeTestRule.onNode(LogScreen.LogTemplate).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).assertExists()
        composeTestRule.onNode(LogScreen.PasswordInput).assertExists()
        composeTestRule.onNode(LogScreen.NextButton).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).performTextInput("giovanni18@pochta.com")
        composeTestRule.onNode(LogScreen.PasswordInput).performTextInput("1         ")
        composeTestRule.onNode(LogScreen.NextButton).performClick()
        composeTestRule.onNode(LogScreen.PasswordErrorToast).assertExists()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertDoesNotExist()
    }

    @Test
    fun testLogScreensBadUser(){
        composeTestRule.setContent { MainScreen(viewModel) }

        composeTestRule.onNode(RegScreen.RegTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).assertExists()
        composeTestRule.onNode(RegScreen.AlreadyTemplate).performClick()
        //LoginScreen
        composeTestRule.onNode(LogScreen.LogTemplate).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).assertExists()
        composeTestRule.onNode(LogScreen.PasswordInput).assertExists()
        composeTestRule.onNode(LogScreen.NextButton).assertExists()
        composeTestRule.onNode(LogScreen.EmailInput).performTextInput("notlogged@pochta.com")
        composeTestRule.onNode(LogScreen.PasswordInput).performTextInput("12345678")
        composeTestRule.onNode(LogScreen.NextButton).performClick()
        composeTestRule.onNode(LogScreen.LoginErrorToast).assertExists()
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertDoesNotExist()
    }

}