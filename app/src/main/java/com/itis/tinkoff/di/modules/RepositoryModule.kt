package com.itis.tinkoff.di.modules

import com.itis.tinkoff.data.remote.OrdersRepositoryImpl
import com.itis.tinkoff.data.remote.ProductsManagingRepositoryImpl
import com.itis.tinkoff.data.remote.ProductsRepositoryImpl
import com.itis.tinkoff.data.remote.UsersRepositoryImpl
import com.itis.tinkoff.domain.repositories.OrdersRepository
import com.itis.tinkoff.domain.repositories.ProductsManagingRepository
import com.itis.tinkoff.domain.repositories.ProductsRepository
import com.itis.tinkoff.domain.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindOrdersRepository(
        impl: OrdersRepositoryImpl
    ): OrdersRepository

    @Binds
    fun bindProductsRepository(
        impl: ProductsRepositoryImpl
    ): ProductsRepository

    @Binds
    fun bindUsersRepository(
        impl: UsersRepositoryImpl
    ): UsersRepository

    @Binds
    fun bindProductsManagingRepository(
        impl: ProductsManagingRepositoryImpl
    ): ProductsManagingRepository
}
