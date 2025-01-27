package com.example.dropenergy

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText


//Объекты на экранах

object LogScreen{
    val LogTemplate = (hasText("Войти") or hasText("Sign in")) and hasNoClickAction()
    val EmailInput = (hasText("Электронная почта") or hasText("Email")) and hasSetTextAction()
    val PasswordInput = (hasText("Пароль") or hasText("Password")) and hasSetTextAction()
    val NextButton = (hasText("Дальше") or hasText("Next")) and hasClickAction()

    val EmailErrorToast = (hasText("Проверьте логин на ошибки")
            or hasText("Check email for errors"))and hasNoClickAction()
    val ShortPasswordErrorToast = (hasText("Пароль должен содержать минимум 8 символов")
            or hasText("Password must contain at least 8 characters"))and hasNoClickAction()
    val PasswordErrorToast = (hasText("Password must contain at least 8 characters")
            or hasText("Password must not contain spaces"))and hasNoClickAction()
    val LoginErrorToast = (hasText("Неверный логин или пароль")
            or hasText("Invalid email or password"))and hasNoClickAction()
}


object RegScreen{
    val RegTemplate = (hasText("Создать аккаунт") or hasText("Sign up")) and hasNoClickAction()
    val EmailInput = (hasText("Электронная почта") or hasText("Email")) and hasSetTextAction()
    val PasswordInput = (hasText("Пароль") or hasText("Password")) and hasSetTextAction()
    val NextButton = (hasText("Дальше") or hasText("Next")) and hasClickAction()
    val AlreadyTemplate = (hasText("Уже есть аккаунт? Войти") or
            hasText("Already have an account? Sign in")) and hasClickAction()
}


object DCansScreen{
    val regCansTemplate = (hasText("Сколько вы покупаете энергетиков в день?")
            or hasText("How many energy drinks do you buy per day?")) and hasNoClickAction()
    val countInput = (hasText("Количество") or hasText("Count")) and hasSetTextAction()
    val nextButton = (hasText("Дальше") or hasText("Next")) and hasClickAction()
}

object DMoneyScreen{
    val regMoneyTemplate = (hasText("Сколько в среднем стоит один энергетик?")
            or hasText("What is the average price of one energy drink?")) and hasNoClickAction()
    val priceInput = (hasText("Стоимость") or hasText("Price")) and hasSetTextAction()
    val currencyButton = hasText("₽") and hasClickAction()
    val nextButton = (hasText("Дальше") or hasText("Next")) and hasClickAction()
}


object BottomBar{
    val bottomBarStatButton = (hasText("Статистика") or hasText("Statistics")) and hasClickAction()
    val bottomBarDiaryButton = (hasText("Дневник") or hasText("Diary"))  and hasClickAction()
    val bottomBarAddButton = hasTestTag("add_record") and hasClickAction()
}

object StatScreen{
    val ScreenDayCecTemplate = hasText("Ежедневная отметка") or hasText("Daily Check") and hasNoClickAction()
    val DaysSection = hasText("Эта неделя") and  hasClickAction()
    val ScreenProgressTemplate = hasText("Прогресс") and hasNoClickAction()
    val MoneySec = hasContentDescription("Деньги") and hasClickAction()
    val CanSec = hasContentDescription("Банки") and  hasClickAction()
}

object DiaryScreen{
    val ScreenTemplate = hasText("Дневник") or hasText("Diary") and hasNoClickAction()
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