package com.example.dropenergy.database.viewModel

import androidx.lifecycle.ViewModel
import com.example.dropenergy.database.model.User
import com.example.dropenergy.database.repository.IUserRepository

class DatabaseViewModel(
    private val repository: IUserRepository)
    : ViewModel() {
//    fun getUsers(): List<User> {
//        return repository.getUsers()
//    }
}