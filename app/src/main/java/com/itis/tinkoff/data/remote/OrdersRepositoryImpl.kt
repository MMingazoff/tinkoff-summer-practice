package com.itis.tinkoff.data.remote

import com.itis.tinkoff.domain.models.CartProductModel
import com.itis.tinkoff.domain.models.OrderItemModel
import com.itis.tinkoff.domain.models.OrderModel
import com.itis.tinkoff.domain.models.OrderStatus
import com.itis.tinkoff.domain.models.ProductModel
import com.itis.tinkoff.domain.repositories.OrdersRepository
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor() : OrdersRepository {

    override suspend fun getCart(): List<CartProductModel> = listOf(
        CartProductModel(
            id = 1,
            name = "Product 1",
            price = 100,
            description = "sdfsdfsdfsdfsdf",
            quantity = 2,
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        CartProductModel(
            id = 2,
            name = "Product 2",
            price = 200,
            description = "sdfsdfsdfsdfsdf",
            quantity = 4,
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        CartProductModel(
            id = 3,
            name = "Product 2",
            price = 200,
            description = "sdfsdfsdfsdfsdf",
            quantity = 4,
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        CartProductModel(
            id = 4,
            name = "Product 2",
            price = 200,
            description = "sdfsdfsdfsdfsdf",
            quantity = 4,
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        CartProductModel(
            id = 5,
            name = "Product 2",
            price = 200,
            description = "sdfsdfsdfsdfsdf",
            quantity = 4,
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        CartProductModel(
            id = 6,
            name = "Product 2",
            price = 200,
            description = "sdfsdfsdfsdfsdf",
            quantity = 4,
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        CartProductModel(
            id = 7,
            name = "Product 2",
            price = 200,
            description = "sdfsdfsdfsdfsdf",
            quantity = 4,
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        CartProductModel(
            id = 8,
            name = "Product 2",
            price = 200,
            description = "sdfsdfsdfsdfsdf",
            quantity = 4,
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        )
    )

    override suspend fun addToCart(productId: Int): Int = 3

    override suspend fun removeFromCart(productId: Int): Int = 2

    override suspend fun removeAllFromCart(productId: Int) {
    }

    override suspend fun placeOrder(deliveryAddress: String) = 1

    override suspend fun cancelOrder(orderId: Int) {
    }

    override suspend fun cancelOrderItem(orderItemId: Int) {
        delay(1000)
    }

    override suspend fun getOrder(orderId: Int): OrderModel = OrderModel(
        id = 1,
        orderItems = listOf(
            OrderItemModel(
                id = 1,
                product = ProductModel(
                    id = 1,
                    name = "Product 1",
                    price = 100,
                    description = "sdfsdfsdfsdfsdf",
                    photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
                ),
                status = OrderStatus.New, quantity = 2,
            ),
            OrderItemModel(
                id = 2,
                product = ProductModel(
                    id = 2,
                    name = "Product 2",
                    price = 102220,
                    description = "sdfsdfsdfsdfsdf",
                    photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
                ),
                status = OrderStatus.InDelivery, quantity = 1,
            ),
        ),
        date = Date(),
    )

    override suspend fun getOrders(): List<OrderModel> = listOf(
        OrderModel(
            id = 1,
            orderItems = listOf(
                OrderItemModel(
                    id = 1,
                    product = ProductModel(
                        id = 1,
                        name = "Product 1",
                        price = 100,
                        description = "sdfsdfsdfsdfsdf",
                        photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
                    ),
                    status = OrderStatus.New, quantity = 2,
                ),
                OrderItemModel(
                    id = 2,
                    product = ProductModel(
                        id = 2,
                        name = "Product 2",
                        price = 102220,
                        description = "sdfsdfsdfsdfsdf",
                        photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
                    ),
                    status = OrderStatus.InDelivery, quantity = 1,
                ),
            ),
            date = Date(),
        ),
        OrderModel(
            id = 2,
            orderItems = listOf(
                OrderItemModel(
                    id = 1,
                    product = ProductModel(
                        id = 1,
                        name = "Product 1",
                        price = 100,
                        description = "sdfsdfsdfsdfsdf",
                        photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
                    ),
                    status = OrderStatus.New, quantity = 2,
                ),
                OrderItemModel(
                    id = 2,
                    product = ProductModel(
                        id = 2,
                        name = "Product 2",
                        price = 102220,
                        description = "sdfsdfsdfsdfsdf",
                        photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
                    ),
                    status = OrderStatus.InDelivery, quantity = 1,
                ),
            ),
            date = Date(),
        )
    )

    override suspend fun editAddress(orderItemId: Int, newAddress: String) {
        delay(1000)
    }
}
