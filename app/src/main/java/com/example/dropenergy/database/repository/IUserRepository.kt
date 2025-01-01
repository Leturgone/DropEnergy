package com.example.dropenergy.database.repository

import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User

interface IUserRepository {
    suspend fun writeUser(uid: String,user: User)

    suspend fun getUser(uid: String): User?


    suspend fun updateDiary(uid: String,diaryRecord: DiaryRecord)

    suspend fun updateWeek(uid: String,newDay: CheckDay)

    suspend fun updateSavedCans(uid: String,status: Boolean)

    suspend fun  updateSavedMoney(uid: String,status: Boolean)

    suspend fun getDiary(uid: String): GetDBState<MutableMap<String, DiaryRecord>>

    suspend fun getWeek(uid: String): GetDBState<MutableMap<String,Boolean>>

    suspend fun getSavedCans(uid: String): GetDBState<Int>

    suspend fun getSavedMoney(uid: String): GetDBState<Int>

    suspend fun getCurrency(uid: String): GetDBState<String>

    suspend fun getEverydayCans(uid: String): GetDBState<Int>
    suspend fun getEverydayMoney(uid: String): GetDBState<Int>



}