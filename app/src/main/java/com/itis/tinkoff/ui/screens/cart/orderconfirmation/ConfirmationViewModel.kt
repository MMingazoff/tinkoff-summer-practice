package com.itis.tinkoff.ui.screens.cart.orderconfirmation

import androidx.lifecycle.viewModelScope
import com.itis.tinkoff.domain.usecases.GetCartUseCase
import com.itis.tinkoff.domain.usecases.GetProfileUseCase
import com.itis.tinkoff.domain.usecases.PlaceOrderUseCase
import com.itis.tinkoff.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel<ConfirmationState, ConfirmationAction, ConfirmationEvent>(ConfirmationState()) {

    init {
        event(ConfirmationEvent.LoadCart)
        event(ConfirmationEvent.LoadProfile)
    }

    override fun event(event: ConfirmationEvent) {
        when (event) {
            is ConfirmationEvent.PayOrder -> {
                state { copy(isPlacingOrder = true) }
                viewModelScope.launch {
                    placeOrderUseCase(event.deliveryAddress)
                        .onSuccess { action { ConfirmationAction.Navigate(it) } }
                        .onFailure { state { copy(isPlacingOrder = false) } }
                }
            }

            ConfirmationEvent.LoadCart -> {
                state { copy(isLoading = true) }
                viewModelScope.launch {
                    getCartUseCase()
                        .onSuccess { state { copy(isLoading = false, cartProducts = it) } }
                        .onFailure { state { copy(isLoading = false) } }
                }
            }

            ConfirmationEvent.LoadProfile -> {
                viewModelScope.launch {
                    getProfileUseCase()
                        .onSuccess { state { copy(profile = it) } }
                }
            }
        }
    }
}
