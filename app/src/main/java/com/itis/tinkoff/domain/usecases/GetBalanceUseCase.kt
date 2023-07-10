package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.UsersRepository
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val repository: UsersRepository,
) {

    suspend operator fun invoke() = runCatching { repository.getBalance() }
}
