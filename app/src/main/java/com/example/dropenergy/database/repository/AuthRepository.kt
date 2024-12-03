package com.example.dropenergy.database.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import java.lang.Exception


class AuthRepository(
    private val firebaseAuth: FirebaseAuth
) : IAuthRepository {

    init {
        Log.i("AuthRepository", "AuthRepository создан")
    }


    override suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Log.i("Firebase","Логин выполнен")
            result.user
        } catch (e : Exception){
            Log.e("Firebase","Произошла ошибка при логине")
            null
        }
    }

    override suspend fun signup(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().build())?.await()
            Log.i("Firebase","Регистрация выполнена")
            result.user
        } catch (e : Exception){
            Log.e("Firebase","Произошла ошибка при регистрации")
            null
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
        Log.i("Firebase","Выполнен выход из аккаунта")
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

}