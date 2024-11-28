package com.example.dropenergy.database.repository

import android.util.Log
import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User
import com.google.firebase.database.DatabaseReference

class UserRepository(
    private val database: DatabaseReference
) : IUserRepository{
    override suspend fun writeUser(user: User) {
        database.child("users").child(user.Uid).setValue(user)
        Log.i("Firebase","Пользователь загружен в БД")
    }

    override suspend fun getUser(user: User) {
        Log.i("Firebase","Данные пользователя получены из БД")
        TODO("Not yet implemented")
    }

    override suspend fun updateDiary(diaryRecord: DiaryRecord) {
        TODO("Not yet implemented")
    }

    override suspend fun updateWeek(newDay: CheckDay) {
        TODO("Not yet implemented")
    }


}