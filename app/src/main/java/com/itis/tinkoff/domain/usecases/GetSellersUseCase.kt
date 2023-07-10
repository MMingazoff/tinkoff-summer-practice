package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetSellersUseCase @Inject constructor(
    private val repository: ProductsRepository,
) {

    suspend operator fun invoke() = runCatching { repository.getAllSellers() }
}
