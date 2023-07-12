package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.ProductsManagingRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: ProductsManagingRepository,
) {

    suspend operator fun invoke(
        name: String,
        description: String,
        price: Int,
        photo: String,
    ) = runCatching { repository.add(name, description, price, photo) }
}
