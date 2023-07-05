package com.itis.tinkoff.ui.screens.home

import com.itis.tinkoff.domain.models.ProductModel

data class HomeState(
    val isLoading: Boolean = false,
    val isLastPage: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val filteredProducts: List<ProductModel> = emptyList(),
    val page: Int = 1,
)
