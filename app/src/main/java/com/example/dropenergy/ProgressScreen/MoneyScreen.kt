package com.example.dropenergy.ProgressScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun MoneyScreen(){
    var ekonom_money = 0
    var in_day_money = 0
    var in_week_money = 0
    var in_mounth_money = 0
    var in_year_money = 0

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