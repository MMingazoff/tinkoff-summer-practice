package com.itis.tinkoff.domain.repositories

interface ProductsManagingRepository {

    suspend fun add(
        name: String,
        description: String,
        price: Int,
        photo: String,
    )
}
