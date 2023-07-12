package com.itis.tinkoff.domain.models

data class CartProductModel(
    val id: Int,
    val product: ProductModel,
    val quantity: Int,
)
