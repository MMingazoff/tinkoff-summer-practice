package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.OrdersRepository
import javax.inject.Inject

class CancelOrderItemUseCase @Inject constructor(
    private val repository: OrdersRepository,
) {

    suspend operator fun invoke(orderItemId: Int) = runCatching {
        repository.cancelOrderItem(orderItemId)
    }
}

