package com.example.dropenergy.database.OptionsScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropenergy.data.OptionsFunction


fun funnyFun(){
    Log.i("Options","FUNNY")
}
@Preview(showBackground = true)
@Composable
fun OptionsScreen(){
    val optList = listOf(
        OptionsFunction(
            title = "Выйти из аккаунта"
        ) { funnyFun() }

    )


    val ctx = LocalContext.current

    Column() {
        Text(text = "Настройки",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
    ) {
        LazyColumn() {
            items(optList.size) {
                val option = optList[it]
                Column(Modifier.padding(start = 13.dp).clickable {option.function()}.fillMaxWidth()){
                    Text(text = option.title,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}