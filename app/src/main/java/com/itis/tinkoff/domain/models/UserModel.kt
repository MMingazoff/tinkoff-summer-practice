package com.itis.tinkoff.domain.models

class UserModel(
    val name: String,
    val email: String,
    val password: String,
    var balance: Int,
    val role: User,
)
