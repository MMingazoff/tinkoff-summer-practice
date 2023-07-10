package com.itis.tinkoff.ui.screens.cart.cart

import com.itis.tinkoff.domain.models.CartProductModel

data class CartState(
    val isLoading: Boolean = false,
    val cartProducts: List<CartProductModel> = emptyList(),
)
