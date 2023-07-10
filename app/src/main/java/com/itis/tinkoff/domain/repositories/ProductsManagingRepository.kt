package com.itis.tinkoff.domain.repositories

import okhttp3.MultipartBody

interface ProductsManagingRepository {

    suspend fun add(
        name: String,
        description: String,
        price: Int,
        photo: MultipartBody.Part
    )
}
