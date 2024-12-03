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
        database.child("users").child(uid).setValue(user).addOnSuccessListener {
            Log.i("Firebase","Пользователь загружен в БД")
        }.addOnFailureListener {
            Log.e("Firebase","Не удалось загрузить в БД")
        }
    }

    override suspend fun getUser(uid:String): User? {
        database.child("users").child(uid).get().addOnSuccessListener {
            val user = it.value
            Log.i("Firebase","Данные пользователя получены из БД $user")
        }.addOnFailureListener {
            Log.e("Firebase","Не удалось загрузить из БД")
        }
        return null
    }


    override suspend fun updateDiary(uid: String,diaryRecord: DiaryRecord) {
        TODO("Not yet implemented")
    }

    override suspend fun updateWeek(uid: String,newDay: CheckDay) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSavedCans(uid: String, newCans: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSavedMoney(uid: String, newMoney: Int) {
        TODO("Not yet implemented")
    }


}