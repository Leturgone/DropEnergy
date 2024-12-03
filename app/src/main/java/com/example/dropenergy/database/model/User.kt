package com.example.dropenergy.database.model

import com.example.dropenergy.data.CheckDay
import com.example.dropenergy.data.DiaryRecord

data class User(
    val login: String,
    val password: String,
    var energy_count: Int?,
    var energy_money: Int?,
    var currency: String?,
    var diary: MutableMap<String,DiaryRecord>,
    var week: List<CheckDay>,
    var saved_money: Int,
    var saved_cans: Int
)

