package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.OrdersRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val repository: OrdersRepository,
) {

    suspend operator fun invoke(productId: Int) = runCatching {
        repository.removeFromCart(productId)
    }
}
