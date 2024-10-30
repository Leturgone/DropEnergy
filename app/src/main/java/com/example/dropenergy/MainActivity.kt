package com.example.dropenergy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dropenergy.AddRecordScreen.AddRecordScreen
import com.example.dropenergy.DiaryScreen.DiaryScreen
import com.example.dropenergy.ProgressScreen.DailyCheckSection
import com.example.dropenergy.ProgressScreen.ProgressSection
import com.example.dropenergy.ui.theme.DropEnergyTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

lateinit var main_padding: PaddingValues
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropEnergyTheme {
                SetBarColor(color = MaterialTheme.colorScheme.background)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun MainScreen(){
    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) {padding ->
        ProgressScreen()

    }
}

@Composable
fun ProgressScreen(){
    Column(
        modifier  = Modifier
            .fillMaxSize()
//            .padding(main_padding) почему то все пропадает
    ) {
        DailyCheckSection()
        Spacer(modifier =Modifier.height(16.dp))
        ProgressSection()
    }
}





@Composable
private fun SetBarColor(color : Color){
    //Функция для изменения цвета статусбара
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = color)
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DropEnergyTheme {
    }
}
