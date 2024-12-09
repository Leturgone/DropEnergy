package com.example.dropenergy.ProgressScreen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.dropenergy.database.viewModel.DBViewModel



@Composable
fun DailyCheckSection(viewModel: DBViewModel) {
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
                    items(viewModel.dayCheckMap.keys.toList().size){
                        val day = viewModel.dayCheckMap.keys.toList()[it]
                        val check = viewModel.dayCheckMap[day]
                        Column(verticalArrangement = Arrangement.Center) {
                            var tint = MaterialTheme.colorScheme.secondaryContainer
                            if (check == true){
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


