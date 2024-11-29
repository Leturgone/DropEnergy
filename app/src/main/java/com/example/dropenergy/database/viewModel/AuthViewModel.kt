package com.example.dropenergy.database.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dropenergy.database.model.User
import com.example.dropenergy.database.repository.IAuthRepository
import com.example.dropenergy.database.repository.IUserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: IAuthRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

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

    fun createUser(login: String, password: String){
        processing_user.value = User(login,password,
            null,null,null, mutableMapOf(), listOf())
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