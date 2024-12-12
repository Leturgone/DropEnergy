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

    suspend fun getDiary(uid: String): GetDBState<MutableMap<String, DiaryRecord>>

    suspend fun getWeek(uid: String): MutableMap<String,Boolean>?

    suspend fun getSavedCans(uid: String): Int?

    suspend fun getSavedMoney(uid: String): Int?

    suspend fun getCurrency(uid: String): String?

    suspend fun getEnergyCount(uid: String): Int?
    suspend fun getEnergyMoney(uid: String): Int?



}