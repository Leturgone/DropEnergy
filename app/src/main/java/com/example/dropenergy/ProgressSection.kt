package com.example.dropenergy

import android.graphics.Color
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropenergy.data.ProgressCategory
import com.example.dropenergy.ui.theme.Yellow40


@Preview
@Composable
fun ProgressSection(){
    val progressCategories = listOf(
        ProgressCategory(
            categoryName = "Деньги",
            icon = Icons.Rounded.AttachMoney,
            backgroundOfIcon = Yellow40
        ),
        ProgressCategory(
            categoryName = "Банки",
            icon = ImageVector.vectorResource(R.drawable.can),
            backgroundOfIcon = Yellow40
        )
    )

    Column {
        Text(text = "Прогресс",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow{
            items(progressCategories.size){
                val cat = progressCategories[it]
                var lastPaddingEnd = 0.dp
                if (it == progressCategories.size - 1){
                    lastPaddingEnd = 16.dp
                }
                //Для элемента
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .size(120.dp)
                        .clickable {}
                        .padding(13.dp), verticalArrangement = Arrangement.SpaceBetween)
                {
                    //Внутренности
                    Box {

                    }
                }
            }
        }

    }

}
