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
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: IAuthRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    val processing_user = MutableLiveData<User>()

    init {
        Log.d("AuthViewModel", "AuthViewModel создана")
    }

    private val _loginFlow = MutableStateFlow<FirebaseUser?>(null)

    val loginFlow: StateFlow<FirebaseUser?> = _loginFlow

    private val _signupFlow = MutableStateFlow<FirebaseUser?>(null)

    val signupFlow: StateFlow<FirebaseUser?> = _signupFlow

    val currentUser = authRepository.getCurrentUser()

    init {
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

    fun updateUser(energy_count: Int, energy_money: Int,
                   currency: String, diary: Map<Int, DiaryRecord>, week: List<CheckDay>) = viewModelScope.launch {
        currentUser?.let { userRepository.updateUser(it.uid,energy_count,energy_money, currency,diary, week) }
    }


    fun logout(){
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }


}