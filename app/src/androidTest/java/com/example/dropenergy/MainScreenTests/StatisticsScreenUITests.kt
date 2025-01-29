package com.example.dropenergy.MainScreenTests

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.example.dropenergy.CanScreen
import com.example.dropenergy.MainScreen
import com.example.dropenergy.MoneyScreen
import com.example.dropenergy.StatScreen
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User
import com.example.dropenergy.database.repository.AuthRepository
import com.example.dropenergy.database.repository.GetDBState
import com.example.dropenergy.database.repository.UserRepository
import com.example.dropenergy.database.viewModel.DBViewModel
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class StatisticsScreenUITests {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val authRepository = mockk<AuthRepository>()
    private val userRepository = mockk<UserRepository>()
    private lateinit var viewModel: DBViewModel
    private lateinit var user: User

    @Before
    fun setUp(){
        user = User(
            login = "user@pochta.com",
            password = "12345678",
            everydayCans = 2,
            everydayMoney = 60,
            currency = "₽",
            diary = mutableMapOf(
                "2025-01-11 18:37" to DiaryRecord(date = "2025-01-11 18:37", recordColor = "green", intensive = "8"),
                "2025-01-10 16:41" to DiaryRecord(date = "2025-01-10 16:41", recordColor = "green", intensive = "8"),
                "2025-01-10 19:43" to DiaryRecord(date = "2025-01-10 19:43", recordColor = "green", intensive = "8"),
                "2025-01-11 18:02" to DiaryRecord(date = "2025-01-11 18:02", recordColor = "red", intensive = "8"),
                "2025-01-11 18:13" to DiaryRecord(date = "2025-01-11 18:13", recordColor = "green", intensive = "8"),
                "2025-01-16 18:41" to DiaryRecord(date = "2025-01-16 18:41", recordColor = "green", intensive = "8"),
                "2025-01-11 18:11" to DiaryRecord(date = "2025-01-11 18:11", recordColor = "red", intensive = "8"),
                "2025-01-27 17:32" to DiaryRecord(date = "2025-01-27 17:32", recordColor = "green", intensive = "8"),
                "2025-01-15 20:41" to DiaryRecord(date = "2025-01-15 20:41", recordColor = "green", intensive = "8"),
                "2025-01-10 17:49" to DiaryRecord(date = "2025-01-10 17:49", recordColor = "green", intensive = "8"),
                "2025-01-10 16:39" to DiaryRecord(date = "2025-01-10 16:39", recordColor = "green", intensive = "8"),
                "2025-01-26 23:50" to DiaryRecord(date = "2025-01-26 23:50", recordColor = "yellow", intensive = "8")
            ),
            week = mutableMapOf("MO" to false, "TU" to true, "WE" to false, "TH" to true, "FR" to false, "SA" to true, "SU" to false),
            savedMoney = 120,
            savedCans = 2
        )
        val mockFirebaseUser = mockk<FirebaseUser>()
        every { authRepository.getCurrentUser() } returns mockFirebaseUser
        every { mockFirebaseUser .uid } returns "uid"
        coEvery { userRepository.getUser("uid")} returns user

    }

    @Test
    fun loggedLaunchTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        viewModel = DBViewModel(authRepository,userRepository)
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
    }


    @Test
    fun loadWeekTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        viewModel = DBViewModel(authRepository,userRepository)
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
        composeTestRule.onNode(StatScreen.WeekLoadError).assertDoesNotExist()
    }

    @Test
    fun errorLoadWeekTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Failure(Exception())
        viewModel = DBViewModel(authRepository,userRepository)
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
        composeTestRule.onNode(StatScreen.WeekLoadError).assertExists()
    }

    @Test
    fun loadMoneyTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        coEvery { userRepository.getSavedMoney("uid")} returns GetDBState.Success(user.savedMoney)
        coEvery { userRepository.getCurrency("uid")} returns GetDBState.Success(user.currency!!)
        coEvery { userRepository.getEverydayMoney("uid")} returns GetDBState.Success(user.everydayMoney!!)
        viewModel = DBViewModel(authRepository,userRepository)
        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).performClick()
        //MoneyScreen
        composeTestRule.onNode(MoneyScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode(MoneyScreen.PrognozTemplate).assertExists()

        composeTestRule.onNode(MoneyScreen.LoadingCurrencyError).assertDoesNotExist()
        composeTestRule.onNode(MoneyScreen.LoadingEveryDayMoneyError).assertDoesNotExist()
        composeTestRule.onNode(MoneyScreen.LoadingSavedMoneyError).assertDoesNotExist()
    }
    @Test
    fun errorLoadSavedMoneyTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        coEvery { userRepository.getSavedMoney("uid")} returns GetDBState.Failure(Exception())
        coEvery { userRepository.getCurrency("uid")} returns GetDBState.Success(user.currency!!)
        coEvery { userRepository.getEverydayMoney("uid")} returns GetDBState.Success(user.everydayMoney!!)
        viewModel = DBViewModel(authRepository,userRepository)

        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).performClick()
        //MoneyScreen
        composeTestRule.onNode(MoneyScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode(MoneyScreen.PrognozTemplate).assertExists()

        composeTestRule.onNode(MoneyScreen.LoadingSavedMoneyError).assertExists()
        composeTestRule.onNode(MoneyScreen.LoadingCurrencyError).assertDoesNotExist()
        composeTestRule.onNode(MoneyScreen.LoadingEveryDayMoneyError).assertDoesNotExist()

    }

    @Test
    fun errorLoadCurrencyTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        coEvery { userRepository.getSavedMoney("uid")} returns GetDBState.Success(user.savedMoney)
        coEvery { userRepository.getCurrency("uid")} returns GetDBState.Failure(Exception())
        coEvery { userRepository.getEverydayMoney("uid")} returns GetDBState.Success(user.everydayMoney!!)
        viewModel = DBViewModel(authRepository,userRepository)

        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).performClick()
        //MoneyScreen
        composeTestRule.onNode(MoneyScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode(MoneyScreen.PrognozTemplate).assertExists()

        composeTestRule.onNode(MoneyScreen.LoadingSavedMoneyError).assertDoesNotExist()
        composeTestRule.onNode(MoneyScreen.LoadingCurrencyError).assertExists()
        composeTestRule.onNode(MoneyScreen.LoadingEveryDayMoneyError).assertDoesNotExist()
    }
    @Test
    fun errorLoadEveryDayMoneyTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        coEvery { userRepository.getSavedMoney("uid")} returns GetDBState.Success(user.savedMoney)
        coEvery { userRepository.getCurrency("uid")} returns GetDBState.Success(user.currency!!)
        coEvery { userRepository.getEverydayMoney("uid")} returns GetDBState.Failure(Exception())
        viewModel = DBViewModel(authRepository,userRepository)

        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).assertExists()
        composeTestRule.onNode(StatScreen.MoneySec).performClick()
        //MoneyScreen
        composeTestRule.onNode(MoneyScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode(MoneyScreen.PrognozTemplate).assertExists()

        composeTestRule.onNode(MoneyScreen.LoadingCurrencyError).assertDoesNotExist()
        composeTestRule.onNode(MoneyScreen.LoadingEveryDayMoneyError).assertExists()
        composeTestRule.onNode(MoneyScreen.LoadingSavedMoneyError).assertDoesNotExist()
    }

    @Test
    fun loadCansTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        coEvery { userRepository.getSavedCans("uid")} returns GetDBState.Success(user.savedCans)
        coEvery { userRepository.getEverydayCans("uid")} returns GetDBState.Success(user.everydayCans!!)
        viewModel = DBViewModel(authRepository,userRepository)

        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
        composeTestRule.onNode(StatScreen.CanSec).assertExists()
        composeTestRule.onNode(StatScreen.CanSec).performClick()
        //CansScreen
        composeTestRule.onNode(CanScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode(CanScreen.PrognozTemplate).assertExists()

        composeTestRule.onNode(CanScreen.LoadingSavedCansError).assertDoesNotExist()
        composeTestRule.onNode(CanScreen.LoadingEveryDayCansError).assertDoesNotExist()
    }
    @Test
    fun errorLoadCansTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        coEvery { userRepository.getSavedCans("uid")} returns GetDBState.Failure(Exception())
        coEvery { userRepository.getEverydayCans("uid")} returns GetDBState.Success(user.everydayCans!!)
        viewModel = DBViewModel(authRepository,userRepository)

        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()
        composeTestRule.onNode(StatScreen.CanSec).assertExists()
        composeTestRule.onNode(StatScreen.CanSec).performClick()
        //CansScreen
        composeTestRule.onNode(CanScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode(CanScreen.PrognozTemplate).assertExists()

        composeTestRule.onNode(CanScreen.LoadingSavedCansError).assertExists()
        composeTestRule.onNode(CanScreen.LoadingEveryDayCansError).assertDoesNotExist()
    }

    @Test
    fun errorEverydayLoadCansTest(){
        coEvery { userRepository.getWeek("uid")} returns GetDBState.Success(user.week)
        coEvery { userRepository.getSavedCans("uid")} returns GetDBState.Success(user.savedCans)
        coEvery { userRepository.getEverydayCans("uid")} returns GetDBState.Failure(Exception())
        viewModel = DBViewModel(authRepository,userRepository)

        composeTestRule.setContent { MainScreen(viewModel) }
        composeTestRule.onNode(StatScreen.ScreenDayCecTemplate).assertExists()

        composeTestRule.onNode(StatScreen.CanSec).assertExists()
        composeTestRule.onNode(StatScreen.CanSec).performClick()
        //CansScreen
        composeTestRule.onNode(CanScreen.ScreenTemplate).assertExists()
        composeTestRule.onNode(CanScreen.PrognozTemplate).assertExists()

        composeTestRule.onNode(CanScreen.LoadingSavedCansError).assertDoesNotExist()
        composeTestRule.onNode(CanScreen.LoadingEveryDayCansError).assertExists()
    }

}