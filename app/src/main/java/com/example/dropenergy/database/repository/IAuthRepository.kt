package com.example.dropenergy.database.repository

import com.google.firebase.auth.FirebaseUser

interface IAuthRepository {


    suspend fun login(email: String, password: String): LoginRegState<FirebaseUser>

    suspend fun signup(email: String, password: String): LoginRegState<FirebaseUser>

    fun logout()

    fun getCurrentUser(): FirebaseUser?

}