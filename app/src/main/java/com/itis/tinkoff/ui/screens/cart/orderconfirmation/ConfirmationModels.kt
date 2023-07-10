package com.itis.tinkoff.ui.screens.cart.orderconfirmation


sealed interface ConfirmationAction {

    class Navigate(val orderId: Int) : ConfirmationAction
}

sealed interface ConfirmationEvent {

    class PayOrder(val deliveryAddress: String) : ConfirmationEvent

    object LoadCart : ConfirmationEvent

    object LoadProfile : ConfirmationEvent
}
