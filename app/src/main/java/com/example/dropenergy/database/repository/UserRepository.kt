package com.example.dropenergy.database.repository

import android.util.Log
import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                is HashMap<*, *> ->{
                    val diaryMap = user["diary"] as? MutableMap<String, HashMap<String, String>> ?: mutableMapOf()
                    val diaryRecords = diaryMap.mapValues { (key, innerMap) ->
                        DiaryRecord(
                            date = innerMap["date"] ?: "",
                            recordText = innerMap["recordText"] ?: "",
                            intensive = innerMap["intensive"]?:"")
                    }.toMutableMap()

                    result = User(
                        login = user["login"].toString(),
                        password = user["password"].toString(),
                        everydayCans = user["energy_count"].toString().toInt(),
                        everydayMoney = user["energy_money"].toString().toInt(),
                        currency = user["currency"].toString(),
                        diary = diaryRecords,
                        week = user["week"] as? MutableMap<String, Boolean> ?: mutableMapOf(),
                        savedMoney = user["saved_money"].toString().toInt(),
                        savedCans = user["saved_cans"].toString().toInt()

                    )
                }
            }
            Log.i("Firebase","Данные пользователя получены из БД $user")
        }.addOnFailureListener {
            Log.e("Firebase","Не удалось загрузить из БД")
        }.await()
        return result
    }

    override suspend fun updateDiary(uid: String, diaryRecord: DiaryRecord) {
        val dtf = DateTimeFormatter.ofPattern("HH:mm")
        val tim = dtf.format(LocalDateTime.now()).toString()
        val userDiary = getUser(uid)?.diary
        diaryRecord.date = diaryRecord.date +" " + tim
        userDiary?.put(diaryRecord.date, diaryRecord)
        database.child("users").child(uid).child("diary").setValue(userDiary).addOnSuccessListener {
            Log.i("Firebase","Дневник загружен в БД")
        }.addOnFailureListener {
            Log.e("Firebase","Не удалось загрузить Дневник в БД")
        }

    }

    override suspend fun updateWeek(uid: String, newDay: CheckDay) {
        val userWeek = getUser(uid)?.week
        userWeek?.set(newDay.day, newDay.check)
        database.child("users").child(uid).child("week").setValue(userWeek).addOnSuccessListener {
            Log.i("Firebase","Неделя загружена в БД")
        }.addOnFailureListener {
            Log.e("Firebase","Не удалось загрузить Дневник в БД")
        }
    }

    override suspend fun updateSavedCans(uid: String, status: Boolean) {
        val newCans: Int
        try {
            newCans = when(status) {
                true -> getUser(uid)?.savedCans?.plus(getUser(uid)?.everydayCans!!)!!
                false -> 0
            }
            database.child("users").child(uid).child("saved_cans").setValue(newCans)
                .addOnSuccessListener {
                    Log.i("Firebase", "Сохр банки загружены в БД")

                }.addOnFailureListener {
                Log.e("Firebase", "Не удалось загрузить сохр банки в БД")
            }
        }catch (e:Exception){
            Log.e("Firebase", "Ошибка в сложении банок")
        }
    }

    override suspend fun updateSavedMoney(uid: String, newMoney: Int) {
        getUser(uid)?.savedMoney = newMoney
        database.child("users").child(uid).child("saved_money").setValue(getUser(uid)?.savedMoney).addOnSuccessListener {
            Log.i("Firebase","Сохр деньги загружены в БД")

        }.addOnFailureListener {
            Log.e("Firebase","Не удалось загрузить сохр деньги в БД")
        }
    }

    override suspend fun getDiary(uid: String): GetDBState<MutableMap<String, DiaryRecord>> {
        return try {
            val diary = getUser(uid)?.diary
            Log.i("Firebase","Полученный дневник $diary")
            GetDBState.Success(diary!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении дневника")
            GetDBState.Failure(e)
        }
    }

    override suspend fun getWeek(uid: String): GetDBState<MutableMap<String,Boolean>> {
        return try{
            val week = getUser(uid)?.week
            Log.i("Firebase","Полученная неделя $week")
            GetDBState.Success(week!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении недели")
            GetDBState.Failure(e)
        }
    }

    override suspend fun getSavedCans(uid: String): GetDBState<Int> {
        return try{
            val savedCans = getUser(uid)?.savedCans
            Log.i("Firebase","Полученное количество невыпитых банок $savedCans")
            GetDBState.Success(savedCans!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении количества невыпитых банок")
            GetDBState.Failure(e)
        }
    }

    override suspend fun getSavedMoney(uid: String): GetDBState<Int> {
        return try{
            val savedMoney = getUser(uid)?.savedMoney
            Log.i("Firebase","Полученное сэкономленных денег $savedMoney")
            GetDBState.Success(savedMoney!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в сэкономленных денег ")
            GetDBState.Failure(e)
        }

    }

    override suspend fun getCurrency(uid: String): GetDBState<String> {
        return try{
            val currency = getUser(uid)?.currency
            Log.i("Firebase","Полученная валюта $currency")
            GetDBState.Success(currency!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении валюты")
            GetDBState.Failure(e)
        }
    }

    override suspend fun getEverydayCans(uid: String): GetDBState<Int> {
        return try{
            val energyCount = getUser(uid)?.everydayCans
            Log.i("Firebase","Полученная $energyCount")
            GetDBState.Success(energyCount!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении количества банок")
            GetDBState.Failure(e)
        }
    }

    override suspend fun getEverydayMoney(uid: String): GetDBState<Int> {
        return try{
            val energyMoney = getUser(uid)?.everydayMoney
            val cansCount = getUser(uid)?.everydayCans
            Log.i("Firebase","Полученная ежед деньги $energyMoney")
            GetDBState.Success(energyMoney!! * cansCount!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении количества денег")
            GetDBState.Failure(e)
        }
    }


}