package com.example.dropenergy.EnterDialogScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dropenergy.database.repository.GetDBState
import com.example.dropenergy.database.viewModel.DBViewModel
import com.example.dropenergy.ui.theme.Purple80


@Composable
fun CurrencyListItem(currency: String, isSelected: Boolean, onClick: () -> Unit){
    Row {
        Text(text = currency, modifier = Modifier.weight(1f))
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF1DB954),
                unselectedColor = Color.LightGray
            ),
            modifier = Modifier.size(24.dp)
        )
    }

}


@Composable
fun AskMoneyScreen(navController: NavHostController,viewModel: DBViewModel?){
    var inputText  by remember { mutableStateOf("") }
    var currencyText by remember { mutableStateOf("₽") }
    var showDialog by remember { mutableStateOf(false) }
    var buttonColor by remember { mutableStateOf(Purple80) }
    val currencyList = listOf<String>("₽", "$", "Fr", "¥", "€", "£", "kr", "zł", "₺", "R")

    val signupState = viewModel?.signupFlow?.collectAsState()
    val ctx = LocalContext.current

    Column {
        LinearProgressIndicator(
            progress = 3/3.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.Green
        )
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
                    Row(verticalAlignment = Alignment.CenterVertically){
                        OutlinedTextField(
                            value = inputText,
                            label = { Text(text = "Стоимость")},
                            singleLine = true,
                            keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                inputText = it
                                buttonColor = Color.Green
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        OutlinedButton(onClick = {
                                                 showDialog = true
                                                 }, modifier = Modifier.height(50.dp)) {
                            Text(text = currencyText, fontSize = 20.sp)
                        }

                    }

                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Выберите валюту") },
                        text = {
                               LazyColumn(){
                                   items(items = currencyList){ currency ->
                                       CurrencyListItem(currency, currencyText == currency)
                                       {
                                           currencyText = currency
                                       }
                                   }
                               }
                        },
                        confirmButton = {
                            Button(onClick = {
                                showDialog = false }) {
                                Text("OK")
                            }
                        }
                    )
                }
                Button(onClick = {
                    try {
                        viewModel?.addMoneyInf(currency = currencyText, money = inputText.toInt())
                        viewModel?.signup()
                    }catch (e:java.lang.NumberFormatException){
                        Toast.makeText(ctx,"Введите только число", Toast.LENGTH_SHORT).show()
                    }
                },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                    ) {
                    Text(text = "Дальше")

                }
                Spacer(modifier = Modifier.height(1.dp))
            }
            signupState?.value.let {state ->
                when(state){
                    is GetDBState.Success -> {
                        LaunchedEffect(Unit) {
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.navigate("progress")
                        }

                    }
                    is GetDBState.Loading -> CircularProgressIndicator()
                    is GetDBState.Failure -> Toast.makeText(ctx,"Ошибка", Toast.LENGTH_SHORT).show()
                    else -> {null}
                }

            }
        }
    }
}

