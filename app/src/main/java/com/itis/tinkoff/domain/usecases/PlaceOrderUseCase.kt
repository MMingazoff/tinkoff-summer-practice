package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.OrdersRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val repository: OrdersRepository,
) {

    suspend operator fun invoke(deliveryAddress: String) =
        runCatching { repository.placeOrder(deliveryAddress) }
}
