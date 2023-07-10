package com.itis.tinkoff.data.remote

import com.itis.tinkoff.domain.models.ProfileModel
import com.itis.tinkoff.domain.models.User
import com.itis.tinkoff.domain.repositories.UsersRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor() : UsersRepository {

    override suspend fun getBalance(): Int = 10000

    override suspend fun topUp(amount: Int) {
        delay(2000L)
    }

    override suspend fun getProfile(): ProfileModel =
        ProfileModel(name = "marat", email = "nice@nice")

    override suspend fun logIn(username: String, password: String): User = User.CUSTOMER
}
