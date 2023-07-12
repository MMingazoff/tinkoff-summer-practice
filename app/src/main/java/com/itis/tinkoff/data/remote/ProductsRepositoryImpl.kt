package com.itis.tinkoff.data.remote

import com.itis.tinkoff.data.remote.datasource.Data
import com.itis.tinkoff.domain.models.ProductModel
import com.itis.tinkoff.domain.models.SellerModel
import com.itis.tinkoff.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor() : ProductsRepository {

    override suspend fun getProducts(
        categories: Set<String>,
        sellers: Set<Int>,
        priceRange: IntRange?
    ): List<ProductModel> = Data.products.filter { product ->
        priceRange?.let { product.price in it } ?: true
    }.let {
        if (categories.isNotEmpty())
            Data.products.filter { it.id in setOf(1, 3, 6) }
        else if (sellers.isNotEmpty())
            Data.products.filter { it.id >= 7 }
        else
            Data.products
    }

    override suspend fun getProduct(id: Int): ProductModel = Data.products.first { it.id == id }

    override suspend fun getAllCategories(): List<String> =
        listOf("Чай", "Сладости", "Средства ухода", "Крема", "Электроника")

    override suspend fun getAllSellers(): List<SellerModel> = Data.sellers
}
