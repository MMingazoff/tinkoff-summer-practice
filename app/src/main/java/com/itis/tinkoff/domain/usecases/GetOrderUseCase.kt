package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.OrdersRepository
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val repository: OrdersRepository,
) {

    suspend operator fun invoke(orderId: Int) = runCatching { repository.getOrder(orderId) }
}
