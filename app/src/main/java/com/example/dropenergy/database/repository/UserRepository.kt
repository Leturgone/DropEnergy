package com.example.dropenergy.database.repository

import com.example.dropenergy.database.model.User

class UserRepository : IUserRepository{
    override fun getUsers(): List<User> {
        return arrayListOf()
    }
}