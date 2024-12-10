package com.example.dropenergy.database.repository

import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User

interface IUserRepository {
    suspend fun writeUser(uid: String,user: User)

    suspend fun getUser(uid: String): User?


    suspend fun updateDiary(uid: String,diaryRecord: DiaryRecord)

    suspend fun updateWeek(uid: String,newDay: CheckDay)

    suspend fun updateSavedCans(uid: String,newCans: Int)

    suspend fun  updateSavedMoney(uid: String,newMoney:Int)

    suspend fun getDiary()

    suspend fun getWeek()

    suspend fun getSavedCans()

    suspend fun getSavedMoney()

    suspend fun getCurrency()

    suspend fun getEnergyCount()
    suspend fun getEnergyMoney()



}