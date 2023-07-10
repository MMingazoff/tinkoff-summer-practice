package com.itis.tinkoff.ui.screens.home.productdetails

import androidx.lifecycle.viewModelScope
import com.itis.android.ui.base.BaseViewModel
import com.itis.tinkoff.domain.usecases.AddToCartUseCase
import com.itis.tinkoff.domain.usecases.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val addToCartUseCase: AddToCartUseCase,
) : BaseViewModel<DetailsState, DetailsAction, DetailsEvent>(DetailsState()) {

    override fun event(event: DetailsEvent) {
        when (event) {
            DetailsEvent.AddToCart -> {
                viewModelScope.launch {
                    state.value.product?.id?.let { id ->
                        addToCartUseCase(id)
                            .onSuccess { /*show notification*/ }
                    }
                }
            }

            is DetailsEvent.LoadProduct -> {
                state { copy(isLoading = true) }
                viewModelScope.launch {
                    getProductUseCase(event.productId)
                        .onSuccess { state { copy(isLoading = false, product = it) } }
                        .onFailure { state { copy(isLoading = false) } }
                }
            }
        }
    }
}
