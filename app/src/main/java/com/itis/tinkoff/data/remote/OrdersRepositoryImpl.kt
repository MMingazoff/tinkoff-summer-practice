package com.itis.tinkoff.data.remote

import com.itis.tinkoff.data.remote.datasource.Data
import com.itis.tinkoff.data.remote.datasource.Data.toOrderItems
import com.itis.tinkoff.domain.models.CartProductModel
import com.itis.tinkoff.domain.models.OrderItemModel
import com.itis.tinkoff.domain.models.OrderModel
import com.itis.tinkoff.domain.models.OrderStatus
import com.itis.tinkoff.domain.models.User
import com.itis.tinkoff.domain.repositories.OrdersRepository
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject


class OrdersRepositoryImpl @Inject constructor() : OrdersRepository {

    override suspend fun getCart(): List<CartProductModel> = Data.cart

    override suspend fun addToCart(productId: Int): Int {
        val product = Data.products.first { it.id == productId }
        val quantity = Data.cart.firstOrNull { it.product.id == productId }?.quantity ?: 0
        if (quantity == 0)
            Data.cart.add(
                CartProductModel(
                    id = Data.cart.firstOrNull()?.id?.inc() ?: 1,
                    product = product,
                    quantity = 1,
                )
            )
        else {
            val index = Data.cart.indexOfFirst { it.product.id == productId }
            val old = Data.cart[index]
            val new = CartProductModel(
                id = old.id,
                product = old.product,
                quantity = old.quantity + 1,
            )
            Data.cart.removeAt(index)
            Data.cart[index] = new
        }
        return quantity + 1
    }

    override suspend fun removeFromCart(productId: Int): Int {
        val quantity = Data.cart.firstOrNull { it.product.id == productId }?.quantity ?: 0
        if (quantity == 0)
            return 0

        val index = Data.cart.indexOfFirst { it.product.id == productId }
        val old = Data.cart[index]
        val new = CartProductModel(
            id = old.id,
            product = old.product,
            quantity = old.quantity - 1,
        )
        Data.cart.removeAt(index)

        if (quantity != 1)
            Data.cart[index] = new
        return quantity - 1
    }

    override suspend fun removeAllFromCart(productId: Int) {
        Data.cart.removeIf { it.product.id == productId }
    }

    override suspend fun placeOrder(deliveryAddress: String): Int {
        val id = Data.orders.lastOrNull()?.id ?: 1
        val sum = Data.cart.sumOf { it.quantity * it.product.price }
        check(sum <= (Data.currentUser?.balance ?: 0))
        Data.orders.add(OrderModel(id = id, orderItems = Data.cart.toOrderItems(), date = Date()))
        Data.currentUser?.balance = Data.currentUser?.balance?.minus(sum) ?: 0
        Data.users.find { it.role == User.SELLER }?.let {
            it.balance = it.balance + sum
        }
        Data.cart.removeIf { true }
        return id
    }

    override suspend fun cancelOrder(orderId: Int) {
        Data.orders.removeIf { it.id == orderId }
    }

    override suspend fun cancelOrderItem(orderItemId: Int) {
        val index =
            Data.orders.indexOfFirst { it.orderItems.indexOfFirst { it.id == orderItemId } != -1 }
        val itemIndex = Data.orders[index].orderItems.indexOfFirst { it.id == orderItemId }
        val old = Data.orders[index].orderItems[itemIndex]
        val new = OrderItemModel(
            id = old.id,
            product = old.product,
            status = OrderStatus.Cancelled,
            quantity = old.quantity
        )
        val oldOrder = Data.orders[index]
        val newOrderItems = oldOrder.orderItems.toMutableList().apply {
            removeAt(itemIndex)
            add(itemIndex, new)
        }
        val newOrder =
            OrderModel(id = oldOrder.id, orderItems = newOrderItems, date = oldOrder.date)
        Data.orders.removeAt(index)
        Data.orders[index] = newOrder
    }

    override suspend fun getOrder(orderId: Int): OrderModel =
        Data.orders.find { it.id == orderId } ?: error("")

    override suspend fun getOrders(): List<OrderModel> = Data.orders

    override suspend fun editAddress(orderItemId: Int, newAddress: String) {
        delay(1000)
    }
}
