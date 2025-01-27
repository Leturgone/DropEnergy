package com.example.dropenergy.MainScreenTests

import androidx.compose.ui.test.junit4.createComposeRule
import com.example.dropenergy.database.repository.AuthRepository
import com.example.dropenergy.database.repository.UserRepository
import com.example.dropenergy.database.viewModel.DBViewModel
import io.mockk.mockk
import org.junit.Rule

class StatisticsScreenUITests {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val authRepository = mockk<AuthRepository>()
    private val userRepository = mockk<UserRepository>()
    private lateinit var viewModel: DBViewModel
}