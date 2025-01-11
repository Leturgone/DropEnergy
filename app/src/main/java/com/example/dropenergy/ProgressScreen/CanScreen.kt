package com.example.dropenergy.ProgressScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropenergy.database.repository.GetDBState
import com.example.dropenergy.database.viewModel.DBViewModel
import com.example.dropenergy.R

@Composable
fun CanScreen(viewModel:DBViewModel){
    var ekonom_can by remember { mutableIntStateOf(0) }
    var in_day_can by remember { mutableIntStateOf(0) }
    var in_week_can by remember { mutableIntStateOf(0) }
    var in_mounth_can by remember { mutableIntStateOf(0) }
    var in_year_can by remember { mutableIntStateOf(0) }

    val ctx = LocalContext.current
    LaunchedEffect(Unit){
        viewModel.getSavedCans()
        viewModel.getEverydayCans()
    }


    Column {
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = stringResource(id = R.string.saved_cans),
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                viewModel.savedCansFlow.collectAsState().value.let {state ->
                    when(state){
                        is GetDBState.Success -> {
                            ekonom_can = state.result
                            SavedCans(ekonom_can = ekonom_can)
                        }
                        is GetDBState.Loading ->  CircularProgressIndicator()
                        is GetDBState.Failure -> Toast.makeText(ctx, stringResource(id = R.string.loading_saved_cans_err),
                            Toast.LENGTH_SHORT).show()
                        else -> {null}
                    }
                }

            }

        }

        Text(text = stringResource(id = R.string.predict),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp))

        Column(Modifier.padding(start = 16.dp)) {
            viewModel.everyDayCansFlow.collectAsState().value.let {state ->
                when(state){
                    is GetDBState.Success -> {
                        in_day_can = state.result
                        in_week_can = in_day_can * 7
                        in_mounth_can = in_day_can * 30
                        in_year_can = in_day_can * 365

                        FutureCans(
                            in_day_can = in_day_can,
                            in_week_can = in_week_can,
                            in_mounth_can = in_mounth_can,
                            in_year_can =in_year_can
                        )
                    }
                    is GetDBState.Loading -> CircularProgressIndicator(Modifier.padding(16.dp))
                    is GetDBState.Failure -> Toast.makeText(ctx, stringResource(id = R.string.loading_everyday_cans_err),
                        Toast.LENGTH_SHORT).show()
                    else -> {null}
                }
            }


        }

    }
}


@Composable
fun SavedCans(ekonom_can: Int){
    Text(
        text = "$ekonom_can ${stringResource(id = R.string._cans)}",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun FutureCans(in_day_can: Int,in_week_can: Int, in_mounth_can:Int, in_year_can:Int ){
    Text(text = "$in_day_can ${stringResource(id = R.string._cans)} ${stringResource(id = R.string.in_day)}",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp))

    Text(text = "$in_week_can ${stringResource(id = R.string._cans)} ${stringResource(id = R.string.in_week)} ",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp))

    Text(text = "$in_mounth_can ${stringResource(id = R.string._cans)} ${stringResource(id = R.string.in_month)} ",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp))

    Text(text = "$in_year_can ${stringResource(id = R.string._cans)} ${stringResource(id = R.string.in_year)} ",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp))
}

