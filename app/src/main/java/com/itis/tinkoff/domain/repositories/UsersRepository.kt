package com.itis.tinkoff.domain.repositories

import com.itis.tinkoff.domain.models.ProfileModel
import com.itis.tinkoff.domain.models.User

interface UsersRepository {

    suspend fun getBalance(): Int

    suspend fun topUp(amount: Int)

    suspend fun getProfile(): ProfileModel

    suspend fun logIn(username: String, password: String): User

    suspend fun register(email: String, username: String, password: String, role: User)
}
