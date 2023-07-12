package com.itis.tinkoff.ui.screens.settings.ordershistory

import androidx.lifecycle.viewModelScope
import com.itis.tinkoff.domain.usecases.GetOrdersUseCase
import com.itis.tinkoff.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersHistoryViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
) : BaseViewModel<OrdersHistoryState, OrdersHistoryAction, OrdersHistoryEvent>(OrdersHistoryState()) {

    init {
        event(OrdersHistoryEvent.LoadOrders)
    }

    override fun event(event: OrdersHistoryEvent) {
        when (event) {
            OrdersHistoryEvent.LoadOrders -> {
                state { copy(isLoading = true) }
                viewModelScope.launch {
                    getOrdersUseCase()
                        .onSuccess { state { copy(isLoading = false, orders = it) } }
                        .onFailure { state { copy(isLoading = false) } }
                }
            }
        }
    }
}
