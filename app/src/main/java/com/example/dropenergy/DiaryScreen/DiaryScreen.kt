package com.example.dropenergy.DiaryScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.repository.GetDBState
import com.example.dropenergy.database.viewModel.DBViewModel


var diaryList = listOf(
    DiaryRecord(
        date = "29.10.24 9:55",
        recordText = "Я Справился с соблазном",
        intensive = 8
    ),
    DiaryRecord(
        date = "28.10.24 9:55",
        recordText = "Я Справился с соблазном",
        intensive = 8
    ),
    DiaryRecord(
        date = "26.10.24 9:55",
        recordText = "Я купил энергетик",
        intensive = 10
    )
)

@Composable
fun DiaryScreen(viewModel:DBViewModel){
    var diary by remember { mutableStateOf(listOf<Pair<String,DiaryRecord>>()) }
    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getDiary()
    }
    Column {
        Text(text = "Дневник",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        viewModel.diaryFlow.collectAsState().value.let {state ->
            when(state){
                is GetDBState.Success -> {
                    diary = state.result.toList()
                    //viewModel.stop()
                }
                is GetDBState.Loading -> Toast.makeText(ctx,"Загрузка списка", Toast.LENGTH_SHORT).show()
                is GetDBState.Failure -> Toast.makeText(ctx,"Ошибка загрузки", Toast.LENGTH_SHORT).show()
                else -> {null}
            }
        }
        LazyColumn() {
            items(diary.size) {
                val record = diary[it]
                Column(modifier = Modifier.padding(6.dp)) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.Circle,
                            modifier = Modifier.size(15.dp),
                            tint = Color.LightGray,
                            contentDescription = "Yes"
                        )
                        Row {
                            Text(
                                text = record.second.date,
                                Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = record.second.recordText,
                                Modifier.padding(start = 6.dp)
                            )

                        }
                    }
                    if(record.second.intensive!=null)
                    Text(
                        text = "Интенсивность: ${record.second.intensive}",
                        Modifier.padding(start = 23.dp)
                    )
                }
            }
        }

    }
}


























