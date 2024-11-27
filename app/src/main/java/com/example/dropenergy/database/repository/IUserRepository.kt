package com.example.dropenergy.database.repository

import com.example.dropenergy.database.model.User

interface IUserRepository {
    fun getUsers(): List<User>
}