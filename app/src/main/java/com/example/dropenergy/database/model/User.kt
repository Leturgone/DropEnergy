package com.example.dropenergy.database.model

import com.example.dropenergy.data.DiaryRecord

data class User(
    val login: String,
    val password: String,
    var everydayCans: Int?,
    var everydayMoney: Int?,
    var currency: String?,
    var diary: MutableMap<String,DiaryRecord>,
    var week: MutableMap<String,Boolean>,
    var savedMoney: Int,
    var savedCans: Int
)

