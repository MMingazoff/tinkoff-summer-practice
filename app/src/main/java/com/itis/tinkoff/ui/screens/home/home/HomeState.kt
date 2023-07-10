package com.itis.tinkoff.ui.screens.home.home

import com.itis.tinkoff.domain.models.ProductModel
import com.itis.tinkoff.domain.models.SellerModel

data class HomeState(
    val isLoading: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val filteredProducts: List<ProductModel> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategories: Set<String> = emptySet(),
    val sellers: List<SellerModel> = emptyList(),
    val selectedSellers: Set<String> = emptySet(),
)
