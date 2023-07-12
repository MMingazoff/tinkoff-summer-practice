package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.models.User
import com.itis.tinkoff.domain.repositories.UsersRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: UsersRepository,
) {

    suspend operator fun invoke(email: String, username: String, password: String, role: User) =
        runCatching {
            repository.register(email, username, password, role)
        }
}
