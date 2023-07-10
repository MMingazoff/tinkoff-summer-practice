package com.itis.tinkoff.data.remote

import com.itis.tinkoff.domain.models.ProductModel
import com.itis.tinkoff.domain.models.SellerModel
import com.itis.tinkoff.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor() : ProductsRepository {

    override suspend fun getProducts(
        categories: Set<String>,
        sellers: Set<Int>,
        priceRange: IntRange?
    ): List<ProductModel> = listOf(
        ProductModel(
            id = 1,
            name = "Product 1",
            price = 100,
            description = "sdfsdfsdfsdfsdf",
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        ProductModel(
            id = 2,
            name = "Product 2",
            price = 102220,
            description = "sdfsdfsdfsdfsdf",
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        ProductModel(
            id = 3,
            name = "Product 3",
            price = 20033,
            description = "sdfsdfsdfsdfsdf",
            photo = "https://avatars.mds.yandex.net/i?id=ad4965c4926e4850582834722448ef6e213a62bb-7909006-images-thumbs&n=13"
        ),
        ProductModel(
            id = 4,
            name = "Product 4",
            price = 1022220,
            description = "sdfsdfdsfsfsdfsdfsdfsdfsdf",
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        ProductModel(
            id = 5,
            name = "Product 5",
            price = 102220,
            description = "sdfsdfAAAsdfsdfsdf",
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
        ProductModel(
            id = 6,
            name = "Product 6",
            price = 102220,
            description = "sdfsdfsdfsaAdfsdf",
            photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
        ),
    )

    override suspend fun getProduct(id: Int): ProductModel = ProductModel(
        id = 1,
        name = "Product 1",
        price = 100,
        description = "sdfsdfsdfsdfsdf",
        photo = "https://sun9-75.userapi.com/impf/c624524/v624524661/1ac95/_CgYw8_VowE.jpg?size=1024x768&quality=96&sign=2609538dc92fd126e46cafd5873bd42e&c_uniq_tag=JGQZl9LUb4PgJCfo7KrUS8atuB9hG5M3G1z30rfOuXg&type=album"
    )

    override suspend fun getAllCategories(): List<String> =
        listOf("Filter 1", "Filter 2", "Filter 3", "Filter 4")

    override suspend fun getAllSellers(): List<SellerModel> = listOf(
        SellerModel(id = 1, name = "Seller 1"),
        SellerModel(id = 2, name = "Seller 2"),
        SellerModel(id = 3, name = "Seller 3"),
        SellerModel(id = 4, name = "Seller 4"),
    )
}
