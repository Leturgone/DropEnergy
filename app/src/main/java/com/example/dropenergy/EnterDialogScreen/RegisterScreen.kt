package com.example.dropenergy.EnterDialogScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dropenergy.database.viewModel.DBViewModel
import com.example.dropenergy.ui.theme.Purple40
import com.example.dropenergy.ui.theme.Purple80
import java.time.LocalDate

//@Preview(showBackground = true)
@Composable
fun RegScreen(navController: NavHostController, viewModel: DBViewModel?){
    var loginInputText  by remember { mutableStateOf("") }
    var passwordInputText  by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginOK by remember { mutableStateOf(false) }
    var buttonColor by remember { mutableStateOf(Purple80) }
    val ctx = LocalContext.current

    Column {
        LinearProgressIndicator(
            progress = 1/3.toFloat(),
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
                        text = "Создать аккаунт",
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(60.dp))

                    OutlinedTextField(
                        value = loginInputText,
                        label = { Text("Логин") },
                        singleLine = true,
                        keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
                        onValueChange = {
                            loginInputText = it
                            loginOK = true
                        })



                    Spacer(modifier = Modifier.height(60.dp))

                    OutlinedTextField(
                        value = passwordInputText,
                        singleLine = true,
                        label = { Text("Пароль") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Password),
                        onValueChange = {
                            passwordInputText = it
                            if (loginOK){
                                buttonColor = Color.Green
                            }
                        },
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            val description = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = {passwordVisible = !passwordVisible}){
                                Icon(imageVector  = image, description)
                            }
                        })
                }



                Button(onClick = {
                    if( loginInputText.isEmpty() || !loginInputText.matches("^[A-Za-z0-9@.]+$".toRegex())
                        || loginInputText.matches("\\s".toRegex())){
                        Toast.makeText(ctx,"Логин не должен содержать пробелов",Toast.LENGTH_SHORT).show()
                    }
                    else if( passwordInputText.isEmpty() || !passwordInputText.matches("^[A-Za-z0-9]+$".toRegex())
                        || passwordInputText.matches("\\s".toRegex())){
                        Toast.makeText(ctx,"Пароль не должен содержать пробелов",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        //Загрузка в бд
                        viewModel?.createUser(loginInputText,passwordInputText,LocalDate.now())
                        navController.navigate("dialog_cans")

                    }

                },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text(text = "Дальше")
                }
                Text(text = "Уже есть аккаунт? Войти",
                    color = Purple40,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    })

                Spacer(modifier = Modifier.height(1.dp))


            }
        }
    }
}