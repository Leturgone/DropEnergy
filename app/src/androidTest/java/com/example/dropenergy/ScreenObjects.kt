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
    val DaysSection = hasText("Эта неделя") and  hasClickAction()
    val ScreenProgressTemplate = hasText("Прогресс") and hasNoClickAction()
    val MoneySec = hasContentDescription("Деньги") and hasClickAction()
    val CanSec = hasContentDescription("Банки") and  hasClickAction()
}

object DiaryScreen{
    val ScreenTemplate = hasText("Дневник") and hasNoClickAction()
}

object AddNewRecordScreen{
    val ScreenTemlate = hasText("Создать запись") and hasNoClickAction()
    val NewBuyRecBtn = hasText("Я купил энергетик") and hasClickAction()
    val NewWantRecBtn = hasText("Я хочу энергетик") and hasClickAction()
    val NewGoodRecBtn = hasText("Я справился с соблазном") and hasClickAction()
}

object MoneyScreen{
    val ScreenTemplate = hasText("Сэкономлено") and hasNoClickAction()
    val PrognozTemplate = hasText("Прогноз")
}

object CanScreen{
    val ScreenTemplate = hasText("Не выпито") and hasNoClickAction()
    val PrognozTemplate = hasText("Прогноз")
}

object NewRecordScreen{
    val ScreenTemplateBuy = hasText("Я купил энергетик") and hasNoClickAction()
    val ScreenTemplateWant = hasText("Я хочу энергетик") and hasNoClickAction()
    val ScreenTemplateGood = hasText("Я справился с соблазном") and hasNoClickAction()

    val SaveButton = hasText("Сохранить") and hasClickAction()
}