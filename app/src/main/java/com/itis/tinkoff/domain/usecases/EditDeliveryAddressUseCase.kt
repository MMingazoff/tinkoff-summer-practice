package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.OrdersRepository
import javax.inject.Inject

class EditDeliveryAddressUseCase @Inject constructor(
    private val repository: OrdersRepository,
) {

    suspend operator fun invoke(orderItemId: Int, newAddress: String) = runCatching {
        repository.editAddress(orderItemId, newAddress)
    }
}
