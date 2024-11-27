package com.example.dropenergy.di

import android.app.Application
import com.example.dropenergy.database.repository.AuthRepository
import com.example.dropenergy.database.repository.IAuthRepository
import com.example.dropenergy.database.viewModel.AuthViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.core.logger.Level


val appModule = module {
    single { FirebaseAuth.getInstance() }
    single<IAuthRepository> { AuthRepository(get()) }
    viewModel { AuthViewModel(get()) }
}

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}
