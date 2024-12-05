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

    private val _loginFlow = MutableStateFlow<LoginRegState<FirebaseUser>?>(null)

    val loginFlow: StateFlow<LoginRegState<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<LoginRegState<FirebaseUser>?>(null)

    val signupFlow: StateFlow<LoginRegState<FirebaseUser>?> = _signupFlow

    val currentUser = authRepository.getCurrentUser()



    init {
        Log.d("AuthViewModel", "AuthViewModel создана")
        if (authRepository.getCurrentUser() != null){
            _loginFlow.value = LoginRegState.Success(authRepository.getCurrentUser()!!)
        }
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