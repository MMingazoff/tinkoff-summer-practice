package com.itis.tinkoff.data.remote

import com.itis.tinkoff.data.remote.datasource.Data
import com.itis.tinkoff.domain.models.ProductModel
import com.itis.tinkoff.domain.repositories.ProductsManagingRepository
import javax.inject.Inject

class ProductsManagingRepositoryImpl @Inject constructor() : ProductsManagingRepository {

    override suspend fun add(
        name: String,
        description: String,
        price: Int,
        photo: String,
    ) {
        Data.products.add(
            ProductModel(
                id = Data.products.last().id + 1,
                name = name,
                price = price,
                description = description,
                photo = photo,
            )
        )
    }
}
