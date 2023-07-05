package com.itis.tinkoff.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.itis.android.ui.base.BaseViewModel
import com.itis.tinkoff.domain.models.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeState, HomeAction, HomeEvent>(
        HomeState(
            products = listOf(
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
                    price = 200,
                    description = "sdfsdfsdfsdfsdf",
                    photo = "https://avatars.mds.yandex.net/i?id=ad4965c4926e4850582834722448ef6e213a62bb-7909006-images-thumbs&n=13"
                )
            ), isLastPage = true
        )
    ) {

    private val searchFlow = MutableStateFlow("")

    init {
        searchFlow
            .debounce(500L)
            .onEach(::search)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    override fun event(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadMore -> TODO()
            is HomeEvent.OnProductClick -> TODO()
        }
    }

    private fun search(query: String) {
        state.value.products
            .filter { it.name.contains(query, ignoreCase = true) }
            .let {
                state { copy(filteredProducts = it) }
            }
    }

    fun onQueryChanged(query: String) {
        searchFlow.value = query
    }
}
