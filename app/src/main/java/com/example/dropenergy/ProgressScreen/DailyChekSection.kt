package com.example.dropenergy.ProgressScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.repository.GetDBState
import com.example.dropenergy.database.viewModel.DBViewModel



@Composable
fun DailyCheckSection(viewModel: DBViewModel) {
    var week by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getWeek()
    }

    Column(modifier = Modifier
        .fillMaxWidth()) {
        Text(text = "Ежедневная отметка",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        //Облако с днями недели
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){

            viewModel.weekFlow.collectAsState().value.let {state ->
                when(state){
                    is GetDBState.Success -> {
                        week = state.result.toList()
                    }
                    is GetDBState.Loading -> Toast.makeText(ctx,"Загрузка недели", Toast.LENGTH_SHORT).show()
                    is GetDBState.Failure -> {
                        week =viewModel.dayCheckMap.toList()
                        Toast.makeText(ctx,"Ошибка загрузки недели", Toast.LENGTH_SHORT).show()}
                    else -> {null}
                }
            }


            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable {}
                    .padding(10.dp)
            ) {
                Text(text = "Эта неделя",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp))
                //Список с днями и чеками входа
                LazyRow(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround){
                    items(week.size){
                        val record = week[it]
                        val day = record.first
                        val check = record.second

                        Column(verticalArrangement = Arrangement.Center) {
                            var tint = MaterialTheme.colorScheme.secondaryContainer
                            if (check){
                                tint = Color.Green
                            }
                            Icon(imageVector = Icons.Rounded.CheckCircleOutline,
                                tint = tint, contentDescription = "Yes")
                            Text(text = day,
                                fontSize = 8.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(4.dp))
                        }

                    }
                }

            }
        }
    }


}


