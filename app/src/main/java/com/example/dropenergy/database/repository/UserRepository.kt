package com.example.dropenergy.database.repository

import android.util.Log
import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User
import com.google.firebase.database.DatabaseReference

class UserRepository(
    private val database: DatabaseReference
) : IUserRepository{
    override suspend fun writeUser(uid: String,user: User) {
        database.child("users").child(uid).setValue(user)
        Log.i("Firebase","Пользователь загружен в БД")
    }

    override suspend fun getUser(uid:String): User? {
        Log.i("Firebase","Данные пользователя получены из БД")
        return null
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        uid: String,
        energy_count: Int,
        energy_money: Int,
        currency: String,
        diary: Map<Int, DiaryRecord>,
        week: List<CheckDay>
    ) {
        database.child("users").child(uid).child("energy_count").setValue(energy_count)
        database.child("users").child(uid).child("energy_money").setValue(energy_money)
        database.child("users").child(uid).child("currency").setValue(currency)
        database.child("users").child(uid).child("diary").setValue(diary)
        database.child("users").child(uid).child("week").setValue(week)
    }

    override suspend fun updateDiary(uid: String,diaryRecord: DiaryRecord) {
        TODO("Not yet implemented")
    }

    override suspend fun updateWeek(uid: String,newDay: CheckDay) {
        TODO("Not yet implemented")
    }


}