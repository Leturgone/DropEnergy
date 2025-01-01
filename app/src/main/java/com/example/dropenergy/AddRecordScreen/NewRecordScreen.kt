package com.example.dropenergy.AddRecordScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.viewModel.DBViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi", "UnrememberedMutableState")
@Composable
fun NewRecordScreen(category: String,navController: NavHostController, viewModel: DBViewModel){
    val calendarState = rememberSheetState()
    var sliderValue by remember { mutableFloatStateOf(8f) }
    var dateValue by remember { mutableStateOf(LocalDate.now().toString()) }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ){
        Column {
            Column {
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = category,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(20.dp)
                    )
                }
                Text(text = "Дата записи",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)


                )

                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                    Text(text = dateValue,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)

                    )
                    Button(onClick = {
                        calendarState.show()
                                     },
                        colors = ButtonDefaults.buttonColors(Color.Green)) {
                        Icon(imageVector = Icons.Rounded.CalendarToday,
                            contentDescription ="Calendar" )
                        
                    }
                    CalendarDialog(state = calendarState,
                        config = CalendarConfig(monthSelection = true
                        ),
                        selection = CalendarSelection.Date{date ->
                            dateValue = date.toString()
                            Log.d("SelectedDate","$date")

                        } )

                }

                Text(text = "Оцените интенсивность",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)

                )

                //Ползунок
                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it},
                    valueRange = 0f..10f,
                    steps = 10,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF14B8A6),
                        activeTrackColor = Color(0xFF14B8A6),
                    )
                )
                Text(
                    text = sliderValue.toInt().toString(),
                    fontSize = 14.sp,
                    color = Color(0xFF14B8A6),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier =Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center
                ){
                    Button(onClick = {
                        //Загрузка в БД
                        val record = DiaryRecord(
                            date = dateValue,
                            recordText = category,
                            intensive = sliderValue.toInt().toString() )
                        viewModel.updateDiary(record)
                        //viewModel.updateWeek()
                        when(category) {
                            "Я хочу энергетик" -> {
                                viewModel.updateSavedCans(true)
                                viewModel.updateSavedMoney(true)
                            }
                            "Я купил энергетик" -> {
                                viewModel.updateSavedCans(false)
                                viewModel.updateSavedMoney(false)
                            }
                            "Я справился с соблазном" -> {
                                viewModel.updateSavedCans(true)
                                viewModel.updateSavedMoney(true)
                            }
                        }

                        navController.navigate("progress")
                                     },
                        modifier = Modifier.padding(horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(Color.Green) ) {
                        Text(text = "Сохранить")
                    }
                }
            }
        }
    }
}