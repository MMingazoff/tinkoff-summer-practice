package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.OrdersRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrdersRepository,
) {

    suspend operator fun invoke() = runCatching { repository.getOrders() }
}
