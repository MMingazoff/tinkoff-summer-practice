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
    ): List<ProductModel> = Data.products

    override suspend fun getProduct(id: Int): ProductModel = Data.products.first { it.id == id }

    override suspend fun getAllCategories(): List<String> =
        listOf("Filter 1", "Filter 2", "Filter 3", "Filter 4")

    override suspend fun getAllSellers(): List<SellerModel> = listOf(
        SellerModel(id = 1, name = "Seller 1"),
        SellerModel(id = 2, name = "Seller 2"),
        SellerModel(id = 3, name = "Seller 3"),
        SellerModel(id = 4, name = "Seller 4"),
    )
}
