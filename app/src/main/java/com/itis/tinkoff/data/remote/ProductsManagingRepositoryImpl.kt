package com.itis.tinkoff.data.remote

import com.itis.tinkoff.domain.repositories.ProductsManagingRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ProductsManagingRepositoryImpl @Inject constructor() : ProductsManagingRepository {

    override suspend fun add(
        name: String,
        description: String,
        price: Int,
        photo: MultipartBody.Part
    ) {
    }
}
