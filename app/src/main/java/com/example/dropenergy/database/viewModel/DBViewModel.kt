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
import com.example.dropenergy.database.repository.GetDBState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DBViewModel(
    private val authRepository: IAuthRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    val dayCheckMap = mutableMapOf<String,Boolean>(
        "Mo" to false,
        "Tu" to false,
        "We" to false,
        "Th" to false,
        "Fr" to false,
        "Sa" to false,
        "Su" to false
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

    private val _everydayMoneyFlow = MutableStateFlow<GetDBState<Int>?>(null)

    val everydayMoneyFlow: StateFlow<GetDBState<Int>?> = _everydayMoneyFlow

    private val _currency= MutableStateFlow<GetDBState<String>?>(null)

    val currency: StateFlow<GetDBState<String>?> = _currency


    private val _savedCansFlow = MutableStateFlow<GetDBState<Int>?>(null)

    val savedCansFlow: StateFlow<GetDBState<Int>?> = _savedCansFlow

    private  val _everydayCansFlow = MutableStateFlow<GetDBState<Int>?>(null)

    val everyDayCansFlow: StateFlow<GetDBState<Int>?> = _everydayCansFlow


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
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val date = dtf.format(LocalDateTime.now())
        processing_user.value = User(login,password,
            null,null,null,
            diary = mutableMapOf(
                date to DiaryRecord(
                    date = date, recordText = "Я зарегистрировался в приложении",intensive = ""

                )
            ),
            week = dayCheckMap,0,0)
    }

    fun addMoneyInf(currency:String, money : Int){
        processing_user.value?.currency = currency
        processing_user.value?.everydayMoney = money
    }

    fun addCans(count: Int){
        processing_user.value?.everydayCans = count
    }

    fun updateDiary(diaryRecord: DiaryRecord)  = viewModelScope.launch{
        currentUser?.let { userRepository.updateDiary(it.uid,diaryRecord)}
    }

    fun updateWeek(newDay: CheckDay) = viewModelScope.launch {
        currentUser?.let { userRepository.updateWeek(it.uid,newDay)}
    }

    fun updateSavedCans(status: Boolean) = viewModelScope.launch {
        currentUser?.let {  userRepository.updateSavedCans(it.uid,status)}

    }

    fun updateSavedMoney(status: Boolean)= viewModelScope.launch  {
        currentUser?.let {  userRepository.updateSavedMoney(it.uid, status)}

    }


    //Методы для получения данных из репозитория для БД

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

    
    fun getSavedMoney() = viewModelScope.launch {
        _savedMoneyFlow.value = GetDBState.Loading
        val result = currentUser?.let { userRepository.getSavedMoney(it.uid) }
        _savedMoneyFlow.value = result
    }

    fun getEverydayMoney() = viewModelScope.launch {
        _everydayMoneyFlow.value = GetDBState.Loading
        val result = currentUser?.let { userRepository.getEverydayMoney(it.uid) }
        _everydayMoneyFlow.value = result
    }


    fun getCurrency() = viewModelScope.launch {
        //Добавить логирование
        _currency.value = GetDBState.Loading
        val result = currentUser?.let { userRepository.getCurrency(it.uid) }
        _currency.value = result
    }


    fun getSavedCans() = viewModelScope.launch {
        //Добавить логирование
        _savedCansFlow.value = GetDBState.Loading
        val result = currentUser?.let { userRepository.getSavedCans(it.uid)}
        _savedCansFlow.value = result
    }



    fun getEverydayCans() = viewModelScope.launch {
        //Добавить логирование
        _everydayCansFlow.value = GetDBState.Loading
        val result = currentUser?.let { userRepository.getEverydayCans(it.uid) }
        _everydayCansFlow.value = result
    }



    fun logout(){
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }






}