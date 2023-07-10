package com.itis.tinkoff.domain.models

import java.util.Date

class OrderModel(
    val id: Int,
    val orderItems: List<OrderItemModel>,
    val date: Date,
)
