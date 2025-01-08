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
import com.example.dropenergy.EnterDialogScreen.LoginScreen
import com.example.dropenergy.EnterDialogScreen.RegScreen
import com.example.dropenergy.ProgressScreen.CanScreen
import com.example.dropenergy.ProgressScreen.MoneyScreen
import com.example.dropenergy.database.OptionsScreen.OptionsScreen
import com.example.dropenergy.database.viewModel.DBViewModel


@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController, viewModel: DBViewModel){

    NavHost(
        navController = navController,
        startDestination = "reg",
        modifier = Modifier.padding(innerPadding)
    ) {

        composable("progress") { ProgressScreen(navController,viewModel) }
        composable("diary") { DiaryScreen(viewModel) }
        composable("add_record") { AddRecordScreen(navController,viewModel) }
        composable("moneyScreen") { MoneyScreen(viewModel) }
        composable("canScreen") {  CanScreen(viewModel) }
        composable("want_rec") {  NewRecordScreen(category = "yellow", navController,viewModel) }
        composable("buy_rec") {  NewRecordScreen(category = "red", navController, viewModel) }
        composable("good_rec") {  NewRecordScreen(category = "green", navController, viewModel) }
        composable("reg") { RegScreen(navController,viewModel) }
        composable("login") { LoginScreen(navController,viewModel) }
        composable("dialog_cans") { AskCansScreen(navController,viewModel) }
        composable("dialog_money") { AskMoneyScreen(navController,viewModel) }
        composable("options") { OptionsScreen(navController,viewModel) }

    }
}