package com.example.dropenergy.DiaryScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.dropenergy.data.DiaryRecord


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
@Preview
@Composable
fun DiaryScreen(){
    
}


























