package com.itis.tinkoff.ui.screens.home.productdetails

import com.itis.tinkoff.domain.models.ProductModel

data class DetailsState(
    val isLoading: Boolean = false,
    val product: ProductModel? = null,
)
