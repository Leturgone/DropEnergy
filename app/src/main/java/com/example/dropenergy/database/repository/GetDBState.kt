package com.example.dropenergy.database.repository

sealed class GetDBState<out R> {

    data class Success<out R>(val result: R): GetDBState<R>()

    data class Failure(val exception: Exception) : GetDBState<Nothing>()

    data object Loading : GetDBState<Nothing>()
}