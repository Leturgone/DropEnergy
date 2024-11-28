package com.example.dropenergy.database.repository

import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User

interface IUserRepository {
    suspend fun writeUser(user: User)

    suspend fun getUser(user: User)

    suspend fun updateDiary(diaryRecord: DiaryRecord)

    suspend fun updateWeek(newDay: CheckDay)

}