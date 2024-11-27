package com.example.dropenergy.database.di

import com.example.dropenergy.database.repository.AuthRepository
import com.example.dropenergy.database.repository.IAuthRepository
import com.example.dropenergy.database.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { FirebaseAuth.getInstance() }
    single<IAuthRepository> { AuthRepository(get()) }
    viewModel { AuthViewModel(get())}
}