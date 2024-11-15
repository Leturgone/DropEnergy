package com.example.dropenergy

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText


//Объекты на экранах
object BottomBar{
    val bottomBarStatButton = hasText("Статистика") and hasClickAction()
    val bottomBarDiaryButton = hasText("Дневник") and hasClickAction()
    val bottomBarAddButton = hasTestTag("add_record") and hasClickAction()
}
object StatScreen{
    val ScreenDayCecTemplate = hasText("Ежедневная отметка") and hasNoClickAction()
    val DaysSection = hasText("Эта неделя, Пн, Вт, Ср, Чт, Пт, Сб, Вс") and  hasClickAction()
    val ScreenProgressTemplate = hasText("Погресс") and hasNoClickAction()
    val MoneySec = hasContentDescription("Деньги") and hasClickAction()
    val CanSec = hasContentDescription("Банки") and  hasClickAction()
}

object DiaryScreen{
    val ScreenTemplate = hasText("Дневник") and hasNoClickAction()
}
object MoneyScreen{}