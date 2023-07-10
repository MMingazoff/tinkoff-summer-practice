package com.itis.tinkoff.domain.usecases

import com.itis.tinkoff.domain.repositories.ProductsManagingRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: ProductsManagingRepository,
) {

    suspend operator fun invoke(
        name: String,
        description: String,
        price: Int,
        photo: MultipartBody.Part
    ) = runCatching { repository.add(name, description, price, photo) }
}
