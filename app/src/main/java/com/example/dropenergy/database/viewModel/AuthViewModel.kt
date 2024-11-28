package com.example.dropenergy.database.viewModel

import android.util.Log
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
        val user = result?.uid?.let {uid ->
                User(uid, email, password,null,null, mapOf(), listOf())
        }
        if (user != null) {
            userRepository.writeUser(user)
        }
        _loginFlow.value = result
    }

    fun signup(email: String, password: String) = viewModelScope.launch {
        val result = authRepository.signup(email, password)
        _signupFlow.value = result
    }

    fun logout(){
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }
}