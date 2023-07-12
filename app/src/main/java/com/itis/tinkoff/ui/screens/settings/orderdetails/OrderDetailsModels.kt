package com.itis.tinkoff.ui.screens.settings.orderdetails

import com.itis.tinkoff.domain.models.OrderItemModel

sealed interface OrderDetailsAction

sealed interface OrderDetailsEvent {

    class LoadOrder(val orderId: Int?) : OrderDetailsEvent

    class SelectOrderItem(val orderItem: OrderItemModel) : OrderDetailsEvent

    class EditDeliveryAddress(val newAddress: String) : OrderDetailsEvent

    object ShowCancellationDialog : OrderDetailsEvent

    object HideCancellationDialog : OrderDetailsEvent

    object ShowEditDialog : OrderDetailsEvent

    object HideEditDialog : OrderDetailsEvent

    object CancelOrderItem : OrderDetailsEvent
}
