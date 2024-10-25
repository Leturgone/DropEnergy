package com.example.dropenergy

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.BarChart
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
        title = "Статистика",
        icon = Icons.Rounded.BarChart
    )
)