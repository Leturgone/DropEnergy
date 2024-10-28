package com.example.dropenergy.AddRecordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AddRecordScreen(){
    Column(modifier = Modifier
        .fillMaxWidth()){
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {  }){
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Yellow)
                    .padding(10.dp).fillMaxWidth(), verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Я хочу энергетик",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center)

            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp).clickable {  }){
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Red)
                    .padding(10.dp).fillMaxWidth(), verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Я купил энергетик",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center)

            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp).clickable {  }){
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Green)
                    .padding(10.dp).fillMaxWidth(), verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Я справился с соблазном",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center)

            }
        }
    }
}
