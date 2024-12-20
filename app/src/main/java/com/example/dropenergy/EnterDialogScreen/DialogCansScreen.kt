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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dropenergy.database.viewModel.DBViewModel
import com.example.dropenergy.ui.theme.Purple80

//@Preview(showBackground = true)
@Composable
fun AskCansScreen(navController: NavHostController,viewModel: DBViewModel?){
    var inputText  by remember {mutableStateOf("")}
    var buttonColor by remember { mutableStateOf(Purple80) }
    Column {
        LinearProgressIndicator(
            progress = 2/3.toFloat(),
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
                        text = "Сколько вы покупаете энергетиков в день?",
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    OutlinedTextField(
                        value = inputText,
                        label = { Text(text = "Количество")},
                        singleLine = true,
                        keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            inputText = it
                            buttonColor = Color.Green
                        })
                }
                Button(onClick = {
                    viewModel?.addCans(inputText.toInt())
                    navController.navigate("dialog_money")

                },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text(text = "Дальше")
                    
                }
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}