package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.UsersRepository
import javax.inject.Inject

class TopUpUseCase @Inject constructor(
    private val repository: UsersRepository,
) {

    suspend operator fun invoke(amount: Int) = runCatching { repository.topUp(amount) }
}
