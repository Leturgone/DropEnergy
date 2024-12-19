package com.example.dropenergy.database.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User
import com.example.dropenergy.database.repository.IAuthRepository
import com.example.dropenergy.database.repository.IUserRepository
import com.example.dropenergy.database.repository.GetDBState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class DBViewModel(
    private val authRepository: IAuthRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    val dayCheckMap = mutableMapOf<String,Boolean>(
        "Пн" to false,
        "Вт" to false,
        "Ср" to false,
        "Чт" to false,
        "Пт" to false,
        "Сб" to false,
        "Вс" to false
    )



    private val processing_user = MutableLiveData<User>()

    private val _loginFlow = MutableStateFlow<GetDBState<FirebaseUser>?>(null)

    val loginFlow: StateFlow<GetDBState<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<GetDBState<FirebaseUser>?>(null)

    val signupFlow: StateFlow<GetDBState<FirebaseUser>?> = _signupFlow

    private val _diaryFlow = MutableStateFlow<GetDBState<MutableMap<String, DiaryRecord>>?>(null)

    val diaryFlow: StateFlow<GetDBState<MutableMap<String, DiaryRecord>>?> = _diaryFlow

    private val _weekFlow = MutableStateFlow<GetDBState<MutableMap<String,Boolean>>?>(null)

    val weekFlow: StateFlow<GetDBState<MutableMap<String,Boolean>>?> = _weekFlow

    private val _savedMoneyFlow = MutableStateFlow<GetDBState<Int>?>(null)

    val savedMoneyFlow: StateFlow<GetDBState<Int>?> = _savedMoneyFlow

    private val _savedCansFlow = MutableStateFlow<GetDBState<Int>?>(null)

    val savedCansFlow: StateFlow<GetDBState<Int>?> = _savedCansFlow

    val currentUser = authRepository.getCurrentUser()



    init {
        Log.d("AuthViewModel", "AuthViewModel создана")
        //Проверка логина
//        if (authRepository.getCurrentUser() != null){
//            _loginFlow.value = LoginRegState.Success(authRepository.getCurrentUser()!!)
//        }
    }

    private fun get_uid(state: GetDBState<*>):String?{
        return when(state){
            is GetDBState.Success ->{
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
        _loginFlow.value = GetDBState.Loading
        val result = authRepository.login(email, password)
        processing_user.value = get_uid(result)?.let { userRepository.getUser(it) }
        _loginFlow.value = result
    }

    fun signup() = viewModelScope.launch {
        _signupFlow.value = GetDBState.Loading
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
        userRepository.updateWeek(uid,newDay)
    }

    fun updateSavedCans(uid: String, newCans: Int) = viewModelScope.launch {
        //Добавить логирование
        userRepository.updateSavedCans(uid,newCans)

    }

    fun updateSavedMoney(uid: String, newMoney: Int)= viewModelScope.launch  {
        //Добавить логирование
        userRepository.updateSavedMoney(uid, newMoney)

    }

    fun getDiary() = viewModelScope.launch {
        _diaryFlow.value = GetDBState.Loading
        val result = currentUser?.let {userRepository.getDiary(it.uid)}
        _diaryFlow.value = result

    }

    fun getWeek()= viewModelScope.launch {
        _weekFlow.value = GetDBState.Loading
        val result = currentUser?.let {userRepository.getWeek(it.uid)}
        _weekFlow.value = result
    }
//
//    fun getSavedCans(uid: String): Int? {
//        //Добавить логирование
//        userRepository.getSavedCans(uid)
//
//    }
//

    fun getSavedMoney() = viewModelScope.launch {
        _savedMoneyFlow.value = GetDBState.Loading
        val result = currentUser?.let { userRepository.getSavedMoney(it.uid) }
        _savedMoneyFlow.value = result
    }
//
//    fun getCurrency(uid: String): String? {
//        //Добавить логирование
//        userRepository.getCurrency(uid)
//    }
//
//    fun getEnergyCount(uid: String): Int? {
//        //Добавить логирование
//        userRepository.getEnergyCount(uid)
//
//    }
//
//    fun getEnergyMoney(uid: String): Int? {
//        //Добавить логирование
//        userRepository.getEnergyMoney(uid)
//    }


    fun logout(){
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }






}