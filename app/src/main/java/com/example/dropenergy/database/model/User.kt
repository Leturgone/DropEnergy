package com.example.dropenergy.database.model

import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord

data class User(
    val login: String,
    val password: String,
    val energy_money: Int,
    val currency: String,
    val diary: MutableList<DiaryRecord>,
    val week: List<CheckDay>
)

