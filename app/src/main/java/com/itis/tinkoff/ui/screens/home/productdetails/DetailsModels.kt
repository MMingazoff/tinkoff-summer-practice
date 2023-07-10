package com.itis.tinkoff.ui.screens.home.productdetails


sealed interface DetailsAction {

    object Navigate : DetailsAction
}

sealed interface DetailsEvent {

    class LoadProduct(val productId: Int) : DetailsEvent

    object AddToCart : DetailsEvent
}
