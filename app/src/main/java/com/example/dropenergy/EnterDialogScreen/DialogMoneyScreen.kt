package com.example.dropenergy.EnterDialogScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun AskMoneyScreen(navController: NavHostController){
    var inputText  by remember { mutableStateOf(" ") }
    var currencyText by remember { mutableStateOf("₽") }

    var currensyList = listOf<String>("₽", "$", "Fr", "¥", "€", "£", "kr", "zł", "₺", "R")

    Column {
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            Column( modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "Сколько в среднем стоит один энергетик?",
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    TextField(
                        value = inputText,
                        label = { Text(text = "Стоимость")},
                        keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
//                            val image = if
//                                Icons.Filled.Visibility
//                            else Icons.Filled.VisibilityOff
//
//                            IconButton(onClick = {passwordVisible = !passwordVisible}){
//                                Icon(imageVector  = image, description)
//                            }
                        },
                        onValueChange = {
                            inputText = it
                        })
                }
                Button(onClick = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate("progress")
                    //Загрузка в бд

                }) {
                    Text(text = "Дальше")

                }
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}