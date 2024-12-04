package com.example.dropenergy.database.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord
import com.example.dropenergy.database.model.User
import com.example.dropenergy.database.repository.IAuthRepository
import com.example.dropenergy.database.repository.IUserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class DBViewModel(
    private val authRepository: IAuthRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    val dayCheckList = listOf(
        CheckDay(
            day = "Пн",
            check = true
        ),
        CheckDay(
            day = "Вт",
            check = false
        ),
        CheckDay(
            day = "Ср",
            check = false
        ),
        CheckDay(
            day = "Чт",
            check = false
        ),
        CheckDay(
            day = "Пт",
            check = false
        ),
        CheckDay(
            day = "Сб",
            check = false
        ),
        CheckDay(
            day = "Вс",
            check = false
        )
    )



    private val processing_user = MutableLiveData<User>()

    private val _loginFlow = MutableStateFlow<FirebaseUser?>(null)

    val loginFlow: StateFlow<FirebaseUser?> = _loginFlow

    private val _signupFlow = MutableStateFlow<FirebaseUser?>(null)

    val signupFlow: StateFlow<FirebaseUser?> = _signupFlow

    val currentUser = authRepository.getCurrentUser()

    init {
        Log.d("AuthViewModel", "AuthViewModel создана")
        if (authRepository.getCurrentUser() != null){
            _loginFlow.value = authRepository.getCurrentUser()
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        val result = authRepository.login(email, password)
        if (result != null) {
            processing_user.value =  userRepository.getUser(result.uid)
        }
        _loginFlow.value = result
    }

    fun signup() = viewModelScope.launch {
        processing_user.value?.let {user ->
            val result =  authRepository.signup(user.login, user.password)
            //val user = User(email, password, null, null, null, mapOf(), listOf())
            val uid = result?.uid
            if (uid != null) {
                userRepository.writeUser(uid, user)
            }
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
            week = dayCheckList,0,0)
    }

    fun addMoneyInf(currency:String, money : Int){
        processing_user.value?.currency = currency
        processing_user.value?.energy_money = money
    }

    fun addCans(count: Int){
        processing_user.value?.energy_count = count
    }


    fun logout(){
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }


}