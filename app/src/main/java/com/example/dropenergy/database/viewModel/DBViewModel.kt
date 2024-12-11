package com.example.dropenergy.database.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User
import com.example.dropenergy.database.repository.IAuthRepository
import com.example.dropenergy.database.repository.IUserRepository
import com.example.dropenergy.database.repository.LoginRegState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class DBViewModel(
    private val authRepository: IAuthRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    val dayCheckMap = mutableMapOf<String,Boolean>(
        "Пн" to true,
        "Вт" to true,
        "Ср" to true,
        "Чт" to true,
        "Пт" to true,
        "Сб" to true,
        "Вс" to true
    )



    private val processing_user = MutableLiveData<User>()

    private val _loginFlow = MutableStateFlow<LoginRegState<FirebaseUser>?>(null)

    val loginFlow: StateFlow<LoginRegState<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<LoginRegState<FirebaseUser>?>(null)

    val signupFlow: StateFlow<LoginRegState<FirebaseUser>?> = _signupFlow

    val currentUser = authRepository.getCurrentUser()



    init {
        Log.d("AuthViewModel", "AuthViewModel создана")
        //Проверка логина
//        if (authRepository.getCurrentUser() != null){
//            _loginFlow.value = LoginRegState.Success(authRepository.getCurrentUser()!!)
//        }
    }

    private fun get_uid(state: LoginRegState<*>):String?{
        return when(state){
            is LoginRegState.Success ->{
                try {
                    val user = state.result as FirebaseUser
                    user.uid
                }catch (e:java.lang.Exception){
                    null
                }
            }
            else ->null
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = LoginRegState.Loading
        val result = authRepository.login(email, password)
        processing_user.value = get_uid(result)?.let { userRepository.getUser(it) }
        _loginFlow.value = result
    }

    fun signup() = viewModelScope.launch {
        _signupFlow.value = LoginRegState.Loading
        processing_user.value?.let {user ->
            val result =  authRepository.signup(user.login, user.password)
            get_uid(result)?.let { userRepository.writeUser(it,user) }
            _signupFlow.value = result
        }
    }


    fun createUser(login: String, password: String, now: LocalDate){
        val date = LocalDate.now().toString()
        processing_user.value = User(login,password,
            null,null,null,
            diary = mutableMapOf(
                date to DiaryRecord(
                    date = date, recordText = "Я зарегистрировался в приложении",intensive = null

                )
            ),
            week = dayCheckMap,0,0)
    }

    fun addMoneyInf(currency:String, money : Int){
        processing_user.value?.currency = currency
        processing_user.value?.energy_money = money
    }

    fun addCans(count: Int){
        processing_user.value?.energy_count = count
    }

    fun updateDiary(uid: String, diaryRecord: DiaryRecord)  = viewModelScope.launch{
        userRepository.updateDiary(uid,diaryRecord)
    }

    fun updateWeek(uid: String, newDay: CheckDay) = viewModelScope.launch {
        //Добавить логирование
        //Возможжно надо будет поменять на мап
        //userRepository.updateWeek()

    }

    fun updateSavedCans(uid: String, newCans: Int) {
        //Добавить логирование

    }

    fun updateSavedMoney(uid: String, newMoney: Int) {
        //Добавить логирование

    }

//    fun getDiary(uid: String): MutableMap<String, DiaryRecord>? {
//        //Добавить логирование
//
//    }
//
//    fun getWeek(uid: String): List<CheckDay>? {
//        //Добавить логирование
//
//    }
//
//    fun getSavedCans(uid: String): Int? {
//        //Добавить логирование
//
//    }
//
//    fun getSavedMoney(uid: String): Int? {
//        //Добавить логирование
//
//    }
//
//    fun getCurrency(uid: String): String? {
//        //Добавить логирование
//
//    }
//
//    fun getEnergyCount(uid: String): Int? {
//        //Добавить логирование
//
//    }
//
//    fun getEnergyMoney(uid: String): Int? {
//        //Добавить логирование
//
//    }










    fun logout(){
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }






}