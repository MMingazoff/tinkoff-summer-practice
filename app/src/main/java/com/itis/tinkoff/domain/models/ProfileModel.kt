package com.itis.tinkoff.domain.models

class ProfileModel(
    val name: String,
    val email: String,
)

enum class User {
    CUSTOMER, SELLER
}
