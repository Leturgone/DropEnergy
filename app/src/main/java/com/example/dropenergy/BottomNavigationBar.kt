package com.example.dropenergy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dropenergy.data.BottomNavigation
import com.example.dropenergy.ui.theme.LightGreen


@Composable
fun BottomNavigationBar(navController: NavHostController){

    val items = listOf(
        BottomNavigation(
            title = stringResource(id = R.string.statistics),
            route = "progress",
            icon = Icons.Rounded.BarChart
        ),
        BottomNavigation(
            title = null,
            route = "add_record",
            icon = Icons.Rounded.AddCircle
        ),
        BottomNavigation(
            title = stringResource(id = R.string.diary),
            route = "diary" ,
            icon = Icons.Rounded.Create
        ),
        BottomNavigation(
            title = stringResource(id = R.string.options),
            route = "options",
            icon = Icons.Rounded.Build
        )
    )
    Column {
        Divider(
            color = LightGreen,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        NavigationBar {

            //Отслеживание текушего маршрута
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            Row(modifier = Modifier.background((MaterialTheme.colorScheme.background)))
            {
                items.forEach{ item->
                    NavigationBarItem(selected = currentRoute == item.route,
                        onClick = {
                            navController.popBackStack()
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(imageVector = item.icon,
                                contentDescription =item.route,
                                tint = MaterialTheme.colorScheme.primary)
                        },
                        label = {
                            item.title?.let { Text(text = it, color = MaterialTheme.colorScheme.primary) }
                        })
                }
            }
        }
    }

}