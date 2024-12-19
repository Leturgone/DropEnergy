package com.example.dropenergy.ProgressScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

    //Подгрузка из БД
    var ekonom_money by remember { mutableIntStateOf(0) }
    var in_day_money by remember { mutableIntStateOf(0) }
    var in_week_money by remember { mutableIntStateOf(0) }
    var in_mounth_money by remember { mutableIntStateOf(0) }
    var in_year_money by remember { mutableIntStateOf(0) }

    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getSavedMoney()
    }

    viewModel.savedMoneyFlow.collectAsState().value.let {state ->
        when(state){
            is GetDBState.Success -> {
                ekonom_money = state.result
            }
            is GetDBState.Loading -> Toast.makeText(ctx,"Загрузка сохр денег", Toast.LENGTH_SHORT).show()
            is GetDBState.Failure -> Toast.makeText(ctx,"Ошибка сохр денег", Toast.LENGTH_SHORT).show()
            else -> {null}
        }
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
                Text(
                    text = "$ekonom_money р",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }

        }

        Text(text = "Прогноз",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        Column(Modifier.padding(start = 16.dp)) {
            Text(text = "$in_day_money р в день",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))

            Text(text = "$in_week_money р в неделю ",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))

            Text(text = "$in_mounth_money р в месяц ",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))

            Text(text = "$in_year_money р год ",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))
        }

    }
}