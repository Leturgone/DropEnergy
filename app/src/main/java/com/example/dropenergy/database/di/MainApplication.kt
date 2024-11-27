package com.example.dropenergy.database.di

import android.app.Application
import com.example.dropenergy.database.repository.AuthRepository
import com.example.dropenergy.database.repository.IAuthRepository
import com.example.dropenergy.database.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {

    private val appModule = module {
        single { FirebaseAuth.getInstance() }
        single<IAuthRepository> { AuthRepository(get()) }
        viewModel { AuthViewModel(get()) }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}