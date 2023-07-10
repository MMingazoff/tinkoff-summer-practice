package com.itis.tinkoff.ui.screens.settings.ordershistory

import com.itis.tinkoff.domain.models.OrderModel

data class OrdersHistoryState(
    val isLoading: Boolean = false,
    val orders: List<OrderModel> = emptyList(),
)
