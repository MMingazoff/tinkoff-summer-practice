package com.itis.tinkoff.domain.repositories

import com.itis.tinkoff.domain.models.CartProductModel
import com.itis.tinkoff.domain.models.OrderModel

interface OrdersRepository {

    suspend fun getCart(): List<CartProductModel>

    suspend fun addToCart(productId: Int): Int

    suspend fun removeFromCart(productId: Int): Int

    suspend fun removeAllFromCart(productId: Int)

    suspend fun placeOrder(deliveryAddress: String): Int

    suspend fun cancelOrder(orderId: Int)

    suspend fun cancelOrderItem(orderItemId: Int)

    suspend fun getOrder(orderId: Int): OrderModel

    suspend fun getOrders(): List<OrderModel>

    suspend fun editAddress(orderItemId: Int, newAddress: String)
}
