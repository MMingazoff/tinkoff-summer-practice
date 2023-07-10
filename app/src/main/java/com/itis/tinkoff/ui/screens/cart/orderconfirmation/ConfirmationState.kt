package com.itis.tinkoff.ui.screens.cart.orderconfirmation

import com.itis.tinkoff.domain.models.CartProductModel
import com.itis.tinkoff.domain.models.ProfileModel

data class ConfirmationState(
    val isLoading: Boolean = false,
    val isPlacingOrder: Boolean = false,
    val cartProducts: List<CartProductModel> = emptyList(),
    val profile: ProfileModel? = null,
)
