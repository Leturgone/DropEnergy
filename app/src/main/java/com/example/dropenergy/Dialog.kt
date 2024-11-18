package com.example.dropenergy

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AskMoneyCansScreen(){
    Column {
        TextField(value = "Hello Work", onValueChange = {})

    }
}