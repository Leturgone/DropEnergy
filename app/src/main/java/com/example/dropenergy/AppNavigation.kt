package com.example.dropenergy

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dropenergy.AddRecordScreen.AddRecordScreen
import com.example.dropenergy.AddRecordScreen.NewRecordScreen
import com.example.dropenergy.DiaryScreen.DiaryScreen
import com.example.dropenergy.EnterDialogScreen.AskCansScreen
import com.example.dropenergy.EnterDialogScreen.AskMoneyScreen
import com.example.dropenergy.EnterDialogScreen.LoginRegScreen
import com.example.dropenergy.ProgressScreen.CanScreen
import com.example.dropenergy.ProgressScreen.MoneyScreen
import com.example.dropenergy.database.viewModel.AuthViewModel


@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController, viewModel: AuthViewModel){

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("progress") { ProgressScreen(navController) }
        composable("diary") { DiaryScreen() }
        composable("add_record") { AddRecordScreen(navController) }
        composable("moneyScreen") { MoneyScreen() }
        composable("canScreen") {  CanScreen() }
        composable("want_rec") {  NewRecordScreen(category = "Я хочу энергетик", navController) }
        composable("buy_rec") {  NewRecordScreen(category = "Я купил энергетик", navController) }
        composable("good_rec") {  NewRecordScreen(category = "Я справился с соблазном", navController) }
        composable("login") { LoginRegScreen(navController,viewModel) }
        composable("dialog_cans") { AskCansScreen(navController) }
        composable("dialog_money") { AskMoneyScreen(navController) }

    }
}