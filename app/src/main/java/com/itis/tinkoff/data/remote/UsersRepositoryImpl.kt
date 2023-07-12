package com.itis.tinkoff.data.remote

import com.itis.tinkoff.data.remote.datasource.Data
import com.itis.tinkoff.domain.models.ProfileModel
import com.itis.tinkoff.domain.models.SellerModel
import com.itis.tinkoff.domain.models.User
import com.itis.tinkoff.domain.models.UserModel
import com.itis.tinkoff.domain.repositories.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor() : UsersRepository {

    override suspend fun getBalance(): Int = Data.currentUser?.balance ?: 0

    override suspend fun topUp(amount: Int) {
        Data.currentUser?.balance = Data.currentUser?.balance?.plus(amount) ?: 0
    }

    override suspend fun getProfile(): ProfileModel =
        ProfileModel(name = Data.currentUser?.name ?: "", email = Data.currentUser?.email ?: "")

    override suspend fun logIn(username: String, password: String): User {
        val user = Data.users.find { it.name == username } ?: error("")
        check(user.password == password)
        Data.currentUser = user
        return user.role
    }

    override suspend fun register(email: String, username: String, password: String, role: User) {
        if (role == User.SELLER) Data.sellers.add(
            SellerModel(id = 4, name = username),
        )
        Data.users.add(
            UserModel(
                email = email,
                name = username,
                password = password,
                balance = 0,
                role = role
            )
        )
    }
}
