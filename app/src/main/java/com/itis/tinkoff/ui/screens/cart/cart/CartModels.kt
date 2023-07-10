package com.itis.tinkoff.ui.screens.cart.cart

sealed interface CartAction {

}

sealed interface CartEvent {

    object GetProducts : CartEvent

    class IncreaseProductAmount(val productId: Int) : CartEvent

    class DecreaseProductAmount(val productId: Int) : CartEvent

    class RemoveProduct(val productId: Int) : CartEvent

    object PlaceOrder : CartEvent
}
