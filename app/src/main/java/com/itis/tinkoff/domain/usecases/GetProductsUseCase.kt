package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductsRepository,
) {

    suspend operator fun invoke(
        categories: Set<String>,
        sellers: Set<Int>,
        priceRange: IntRange?,
    ) = runCatching { repository.getProducts(categories, sellers, priceRange) }
}
