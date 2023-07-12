package com.itis.tinkoff.ui.screens.home.home

import androidx.lifecycle.viewModelScope
import com.itis.tinkoff.domain.usecases.GetCategoriesUseCase
import com.itis.tinkoff.domain.usecases.GetProductsUseCase
import com.itis.tinkoff.domain.usecases.GetSellersUseCase
import com.itis.tinkoff.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSellersUseCase: GetSellersUseCase,
) : BaseViewModel<HomeState, HomeAction, HomeEvent>(HomeState()) {

    private val searchFlow = MutableStateFlow("")

    init {
        searchFlow
            .debounce(500L)
            .onEach(::search)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        event(HomeEvent.ApplyFilters())
        event(HomeEvent.GetFilters)
    }

    override fun event(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnProductClick -> {
                action { HomeAction.Navigate(event.productId) }
            }

            is HomeEvent.AddCategoryFilter -> {
                state {
                    copy(selectedCategories = selectedCategories.toMutableSet().apply {
                        if (contains(event.category))
                            remove(event.category)
                        else
                            add(event.category)
                    })
                }
            }

            is HomeEvent.AddSellerFilter -> {
                state {
                    copy(selectedSellers = selectedSellers.toMutableSet().apply {
                        if (contains(event.seller))
                            remove(event.seller)
                        else
                            add(event.seller)
                    })
                }
            }

            HomeEvent.ResetFilters -> {
                state { copy(selectedCategories = emptySet(), selectedSellers = emptySet()) }
            }

            is HomeEvent.ApplyFilters -> {
                viewModelScope.launch {
                    state { copy(isLoading = true) }
                    state.value.run {
                        getProductsUseCase(
                            selectedCategories,
                            getSelectedSellersIds(),
                            event.priceRange
                        )
                            .onSuccess { state { copy(products = it, isLoading = false) } }
                            .onFailure { state { copy(isLoading = false) } }
                    }
                }
            }

            HomeEvent.GetFilters -> {
                viewModelScope.launch {
                    getCategoriesUseCase()
                        .onSuccess { state { copy(categories = it) } }
                }
                viewModelScope.launch {
                    getSellersUseCase()
                        .onSuccess { state { copy(sellers = it) } }
                }
            }
        }
    }

    private fun getSelectedSellersIds() = state.value.run {
        sellers.filter { selectedSellers.contains(it.name) }.map { it.id }.toSet()
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
