package com.itis.tinkoff.domain.repositories

import com.itis.tinkoff.domain.models.ProductModel
import com.itis.tinkoff.domain.models.SellerModel

interface ProductsRepository {

    suspend fun getProducts(
        categories: Set<String>,
        sellers: Set<Int>,
        priceRange: IntRange?
    ): List<ProductModel>

    suspend fun getProduct(id: Int): ProductModel

    suspend fun getAllCategories(): List<String>

    suspend fun getAllSellers(): List<SellerModel>
}
