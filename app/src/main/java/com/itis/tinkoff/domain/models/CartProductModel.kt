package com.itis.tinkoff.domain.models

data class CartProductModel(
    val id: Int,
    val name: String,
    val price: Int,
    val description: String,
    val photo: String,
    val quantity: Int,
)
