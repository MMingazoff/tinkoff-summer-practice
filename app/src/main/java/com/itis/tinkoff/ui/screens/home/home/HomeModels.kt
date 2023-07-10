package com.itis.tinkoff.ui.screens.home.home

sealed interface HomeAction {

    class Navigate(val productId: Int) : HomeAction

}

sealed interface HomeEvent {

    class OnProductClick(val productId: Int) : HomeEvent

    class ApplyFilters(val priceRange: IntRange = 1..Int.MAX_VALUE) : HomeEvent

    object GetFilters : HomeEvent

    class AddCategoryFilter(val category: String) : HomeEvent

    object ResetFilters : HomeEvent

    class AddSellerFilter(val seller: String) : HomeEvent
}
