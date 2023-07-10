package com.itis.tinkoff.ui.screens.settings.orderdetails

import com.itis.tinkoff.domain.models.OrderItemModel
import java.util.Date

data class OrderDetailsState(
    val orderId: Int? = null,
    val isLoading: Boolean = false,
    val orderItems: List<OrderItemModel> = emptyList(),
    val isLoadingOrderItem: Boolean = false,
    val currentOrderItem: OrderItemModel? = null,
    val date: Date? = null,
    val showCancellationDialog: Boolean = false,
    val isCancelling: Boolean = false,
    val showEditDialog: Boolean = false,
    val isEditing: Boolean = false,
)
