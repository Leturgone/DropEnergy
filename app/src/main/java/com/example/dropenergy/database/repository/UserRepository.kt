package com.example.dropenergy.database.repository

import android.util.Log
import androidx.compose.foundation.pager.PageSize.Fill.calculateMainAxisPageSize
import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

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
        var result: User? = null
        database.child("users").child(uid).get().addOnSuccessListener {
            val user = it.value
            when(user){
                is HashMap<*, *> ->
                    result = User(
                        login = user["login"].toString(),
                        password = user["password"].toString(),
                        energy_count = user["energy_count"].toString().toInt(),
                        energy_money = user["energy_money"].toString().toInt(),
                        currency = user["currency"].toString(),
                        diary = user["diary"] as? MutableMap<String, DiaryRecord> ?: mutableMapOf(),
                        week = user["week"] as? List<CheckDay> ?: listOf(),
                        saved_money = user["saved_money"].toString().toInt(),
                        saved_cans = user["saved_cans"].toString().toInt()

                    )
            }
            Log.i("Firebase","Данные пользователя получены из БД $user")
        }.addOnFailureListener {
            Log.e("Firebase","Не удалось загрузить из БД")
        }.await()
        return result
    }

    override suspend fun updateDiary(uid: String, diaryRecord: DiaryRecord) {
        //Добавить логирование
        getUser(uid)?.diary?.put(diaryRecord.date,diaryRecord)
    }

    override suspend fun updateWeek(uid: String, newDay: CheckDay) {
        //Добавить логирование
        //Возможжно надо будет поменять на мап
        TODO("Not yet implemented")
    }

    override suspend fun updateSavedCans(uid: String, newCans: Int) {
        //Добавить логирование
        getUser(uid)?.saved_cans = newCans
    }

    override suspend fun updateSavedMoney(uid: String, newMoney: Int) {
        //Добавить логирование
        getUser(uid)?.saved_money = newMoney
    }

    override suspend fun getDiary(uid: String): MutableMap<String, DiaryRecord>? {
        //Добавить логирование
        return getUser(uid)?.diary
    }

    override suspend fun getWeek(uid: String): List<CheckDay>? {
        //Добавить логирование
        return getUser(uid)?.week
    }

    override suspend fun getSavedCans(uid: String): Int? {
        //Добавить логирование
        return getUser(uid)?.saved_cans
    }

    override suspend fun getSavedMoney(uid: String): Int? {
        //Добавить логирование
        return getUser(uid)?.saved_money
    }

    override suspend fun getCurrency(uid: String): String? {
        //Добавить логирование
        return getUser(uid)?.currency
    }

    override suspend fun getEnergyCount(uid: String): Int? {
        //Добавить логирование
        return getUser(uid)?.energy_count
    }

    override suspend fun getEnergyMoney(uid: String): Int? {
        //Добавить логирование
        return getUser(uid)?.energy_money
    }


}