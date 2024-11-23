package com.example.dropenergy.database

import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord

data class User(
    var login: String,
    var password: String,
    var energy_money: Int,
    var currency: String,
    var diary: MutableList<DiaryRecord>,
    var week: List<CheckDay>
)

