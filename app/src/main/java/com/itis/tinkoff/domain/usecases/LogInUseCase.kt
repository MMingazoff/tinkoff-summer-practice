package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.UsersRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: UsersRepository,
) {

    suspend operator fun invoke(username: String, password: String) = runCatching {
        repository.logIn(username, password)
    }
}
