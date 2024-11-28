package com.example.dropenergy.di

import android.app.Application
import com.example.dropenergy.database.repository.AuthRepository
import com.example.dropenergy.database.repository.IAuthRepository
import com.example.dropenergy.database.repository.IUserRepository
import com.example.dropenergy.database.repository.UserRepository
import com.example.dropenergy.database.viewModel.AuthViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.core.logger.Level


val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { Firebase.database.reference }
    single<IUserRepository> {UserRepository(get())}
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