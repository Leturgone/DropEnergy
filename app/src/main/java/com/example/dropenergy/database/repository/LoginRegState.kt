package com.example.dropenergy.database.repository

sealed class LoginRegState<out R> {
    class Sucsess<out R>(val result: R): LoginRegState<R>()
    data class Failure(val exception: Exception) : LoginRegState<Nothing>()
    data object Loading : LoginRegState<Nothing>()
}