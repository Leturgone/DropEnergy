package com.example.dropenergy.database.repository

import android.util.Log
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
                is HashMap<*, *> ->{
                    val diaryMap = user["diary"] as? MutableMap<String, HashMap<String, String>> ?: mutableMapOf()
                    val diaryRecords = diaryMap.mapValues { (key, innerMap) ->
                        DiaryRecord(
                            date = innerMap["date"] ?: "",
                            recordText = innerMap["recordText"] ?: "",
                            intensive = innerMap["intensive"]?.toIntOrNull()
                        )
                    }.toMutableMap()

                    result = User(
                        login = user["login"].toString(),
                        password = user["password"].toString(),
                        energy_count = user["energy_count"].toString().toInt(),
                        energy_money = user["energy_money"].toString().toInt(),
                        currency = user["currency"].toString(),
                        diary = diaryRecords,
                        week = user["week"] as? MutableMap<String, Boolean> ?: mutableMapOf(),
                        saved_money = user["saved_money"].toString().toInt(),
                        saved_cans = user["saved_cans"].toString().toInt()

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

        val userDiary = getUser(uid)?.diary
        userDiary?.put(diaryRecord.date,diaryRecord)
        database.child("users").child(uid).child("diary").setValue(userDiary).addOnSuccessListener {
            Log.i("Firebase","Дневник загружен в БД")

        }.addOnFailureListener {
            Log.e("Firebase","Не удалось загрузить Дневник в БД")
        }

    }

    override suspend fun updateWeek(uid: String, newDay: CheckDay) {


        getUser(uid)?.week?.set(newDay.day, newDay.check)


    }

    override suspend fun updateSavedCans(uid: String, newCans: Int) {
        //Добавить логирование
        getUser(uid)?.saved_cans = newCans
    }

    override suspend fun updateSavedMoney(uid: String, newMoney: Int) {
        //Добавить логирование
        getUser(uid)?.saved_money = newMoney
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
            val savedCans = getUser(uid)?.saved_cans
            Log.i("Firebase","Полученное количество невыпитых банок $savedCans")
            GetDBState.Success(savedCans!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении количества невыпитых банок")
            GetDBState.Failure(e)
        }
    }

    override suspend fun getSavedMoney(uid: String): GetDBState<Int> {
        return try{
            val savedMoney = getUser(uid)?.saved_money
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
            val energyCount = getUser(uid)?.energy_count
            Log.i("Firebase","Полученная $energyCount")
            GetDBState.Success(energyCount!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении количества банок")
            GetDBState.Failure(e)
        }
    }

    override suspend fun getEverydayMoney(uid: String): GetDBState<Int> {
        return try{
            val energyMoney = getUser(uid)?.energy_money
            val cansCount = getUser(uid)?.energy_count
            Log.i("Firebase","Полученная ежед деньги $energyMoney")
            GetDBState.Success(energyMoney!! * cansCount!!)
        }catch (e:Exception){
            Log.e("Firebase","Ошибка в получении количества денег")
            GetDBState.Failure(e)
        }
    }


}