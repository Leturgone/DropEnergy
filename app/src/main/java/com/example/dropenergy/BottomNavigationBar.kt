package com.example.dropenergy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dropenergy.data.BottomNavigation

val items = listOf(
    BottomNavigation(
        title = "Статистика",
        icon = Icons.Rounded.BarChart
    ),
    BottomNavigation(
        title = null,
        icon = Icons.Rounded.AddCircle
    ),
    BottomNavigation(
        title = "Дневник",
        icon = Icons.Rounded.Create
    )
)
@Preview
@Composable
fun BottomNavigationBar(){
    NavigationBar {
        Row(modifier = Modifier.background((MaterialTheme.colorScheme.inverseOnSurface)))
        {
            items.forEachIndexed{ index,item->
                NavigationBarItem(selected = index ==0,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription =item.title, tint = MaterialTheme.colorScheme.onBackground)
                    },
                    label = {
                        item.title?.let { Text(text = it, color = MaterialTheme.colorScheme.onBackground) }
                    })
            }
        }
    }
}