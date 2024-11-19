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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun LoginRegScreen(navController: NavHostController){
    var loginInputText  = remember { mutableStateOf(" ") }
    var passwordInputText  = remember { mutableStateOf(" ") }
    Column {
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            Column( modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "Войти/Создать аккаунт",
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(60.dp))

                    TextField(
                        value = loginInputText.value,
                        label = { Text("Логин") },
                        keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
                        onValueChange = {
                            loginInputText.value = it
                        })
                    Spacer(modifier = Modifier.height(60.dp))
                    TextField(
                        value = passwordInputText.value,
                        label = { Text("Пароль") },
                        keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Password),
                        onValueChange = {
                            passwordInputText.value = it
                        })
                }
                Button(onClick = {
                    navController.navigate("dialog_cans")
                    //Загрузка в бд


                }) {
                    Text(text = "Дальше")

                }
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}