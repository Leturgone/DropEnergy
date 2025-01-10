package com.example.dropenergy.ProgressScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropenergy.database.repository.GetDBState
import com.example.dropenergy.database.viewModel.DBViewModel

//@Preview
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MoneyScreen(viewModel: DBViewModel){

    var ekonom_money by remember { mutableIntStateOf(0) }
    var in_day_money by remember { mutableIntStateOf(0) }
    var in_week_money by remember { mutableIntStateOf(0) }
    var in_mounth_money by remember { mutableIntStateOf(0) }
    var in_year_money by remember { mutableIntStateOf(0) }
    var currency by remember { mutableStateOf<String>("") }

    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getSavedMoney()
        viewModel.getEverydayMoney()
        viewModel.getCurrency()
    }

    Column {
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Сэкономлено",
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                viewModel.currency.collectAsState().value.let {state ->
                    when(state){
                        is GetDBState.Success -> {
                            currency = state.result
                            viewModel.savedMoneyFlow.collectAsState().value.let {state ->
                                when(state){
                                    is GetDBState.Success -> {
                                        ekonom_money = state.result
                                        SavedMoney(currency = currency, ekonom_money =ekonom_money )
                                    }
                                    is GetDBState.Loading -> CircularProgressIndicator(Modifier.padding(16.dp))
                                    is GetDBState.Failure -> Toast.makeText(ctx,"Ошибка сохр денег", Toast.LENGTH_SHORT).show()
                                    else -> {null}
                                }
                            }

                        }
                        is GetDBState.Loading -> CircularProgressIndicator(Modifier.padding(16.dp))
                        is GetDBState.Failure -> Toast.makeText(ctx,"Ошибка загр валюты", Toast.LENGTH_SHORT).show()
                        else -> {null}
                    }
                }

            }

        }

        Text(text = "Прогноз",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        Column(Modifier.padding(start = 16.dp)) {
            viewModel.everydayMoneyFlow.collectAsState().value.let {state ->
                when(state){
                    is GetDBState.Success -> {
                        in_day_money = state.result
                        in_week_money = in_day_money*7
                        in_mounth_money = in_day_money*30
                        in_year_money = in_day_money*365
                        FutureMoney(
                            currency = currency,
                            in_day_money = in_day_money,
                            in_week_money = in_week_money,
                            in_mounth_money = in_mounth_money,
                            in_year_money = in_year_money
                        )
                    }
                    is GetDBState.Loading -> CircularProgressIndicator(Modifier.padding(16.dp))
                    is GetDBState.Failure -> Toast.makeText(ctx,"Ошибка ежед денег", Toast.LENGTH_SHORT).show()
                    else -> {null}
                }
            }
        }

    }
}

@Composable
fun SavedMoney(currency:String, ekonom_money: Int){
    Text(
        text = "$ekonom_money $currency",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun FutureMoney(currency:String, in_day_money: Int, in_week_money: Int,
                in_mounth_money: Int, in_year_money: Int ){
    Text(text = "$in_day_money $currency в день",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp))

    Text(text = "$in_week_money $currency в неделю ",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp))

    Text(text = "$in_mounth_money $currency в месяц ",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp))

    Text(text = "$in_year_money $currency год ",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp))
}