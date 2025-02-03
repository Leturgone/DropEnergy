# DropEnergy

Мобильное приложение, позволяющее пользователям отказаться от привычки пить энергетики. 

Оно позволяет отслеживать свой прогресс, просматривать прогноз и статистку по невыпитым банкам и деньгам, а также вести дневник, в котором показывается состояние пользователя в момент записи.

![изображение](https://github.com/user-attachments/assets/f75fc5d1-34f3-493c-8656-0d60af266c03)

# Технологический стек
- **Android** – ОС, на которой разработано приложение
- **Android SDK 34** – Средство разработки приложения
- **Kotlin** – Основной язык, на котором разработано прилжение
- **Jetpack Compose** - набор инструментов для построения UI
- **MVVM** - Архитектура
- **Firebase** – NoSQL База данных для обработки входа и регестрации пользователей, а также хранения их данных
- **Koin** - для внедрения зависимостей (DI)  
- **Coroutines + Flow** – для асинхронного обращения к БД
- **ViewModel** - для реализации MVVM архитектуры
- **JUnit + Espresso + RoboElectric + MockK** - для тестирования

# Установка
1. Склонировать данный репозиторий:  
   ```bash
   https://github.com/Leturgone/DropEnergy.git
2. Открыть проект Android Studio.
3. Добавить в каталог app свой google-services.json
4. Запустить приложения на эмуляторе или телефоне
   
  Чтобы провести сброс недели
1. Добавить в каталог weekUpdater ваш adminsdk.json, а также прописать до него путь, и написать свою ссылку на пд в соответсвующих полях в Main.kt
2. Запустить, или собрать в jar файл и запустить на сервере для автоматического сброса

# Интерфейс
Светлая тема с англ локализацией

![изображение](https://github.com/user-attachments/assets/22834d9a-db7d-435f-a515-e39f3c0500dd)

Темная тема с ру локализацией

![изображение](https://github.com/user-attachments/assets/f75fc5d1-34f3-493c-8656-0d60af266c03)


