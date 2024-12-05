package com.example.dropenergy.database.repository

import com.google.firebase.auth.FirebaseUser

sealed class LoginRegState<out R> {
    data class Sucсess<out R>(val result: R): LoginRegState<R>()
    data class Failure(val exception: Exception) : LoginRegState<Nothing>()
    data object Loading : LoginRegState<Nothing>()
}