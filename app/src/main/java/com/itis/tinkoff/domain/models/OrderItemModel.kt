package com.itis.tinkoff.domain.models

import androidx.annotation.StringRes
import com.itis.tinkoff.R

class OrderItemModel(
    val id: Int,
    val product: ProductModel,
    val status: OrderStatus,
    val quantity: Int,
)

enum class OrderStatus {
    New, InDelivery, Received, Cancelled;

    @get:StringRes
    val stringRes
        get() = when (this) {
            New -> R.string.status_new
            InDelivery -> R.string.status_in_delivery
            Received -> R.string.status_received
            Cancelled -> R.string.status_cancelled
        }
}
