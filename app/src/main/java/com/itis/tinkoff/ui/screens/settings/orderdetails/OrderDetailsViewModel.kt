package com.itis.tinkoff.ui.screens.settings.orderdetails

import androidx.lifecycle.viewModelScope
import com.itis.tinkoff.domain.models.OrderItemModel
import com.itis.tinkoff.domain.usecases.CancelOrderItemUseCase
import com.itis.tinkoff.domain.usecases.EditDeliveryAddressUseCase
import com.itis.tinkoff.domain.usecases.GetOrderUseCase
import com.itis.tinkoff.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase,
    private val cancelOrderItemUseCase: CancelOrderItemUseCase,
    private val editDeliveryAddressUseCase: EditDeliveryAddressUseCase,
) : BaseViewModel<OrderDetailsState, OrderDetailsAction, OrderDetailsEvent>(OrderDetailsState()) {

    init {
        event(OrderDetailsEvent.LoadOrder(1))
    }

    override fun event(event: OrderDetailsEvent) {
        when (event) {
            is OrderDetailsEvent.LoadOrder -> {
                if (state.value.orderId != null)
                    return
                state { copy(isLoading = true) }
                if (event.orderId == null)
                    return
                viewModelScope.launch {
                    getOrderUseCase(event.orderId)
                        .onSuccess {
                            state {
                                copy(isLoading = false, orderItems = it.orderItems, date = it.date)
                            }
                        }
                }
            }

            is OrderDetailsEvent.SelectOrderItem -> {
                state { copy(currentOrderItem = event.orderItem) }
            }

            OrderDetailsEvent.HideCancellationDialog -> {
                state { copy(showCancellationDialog = false) }
            }

            OrderDetailsEvent.ShowCancellationDialog -> {
                state { copy(showCancellationDialog = true) }
            }

            OrderDetailsEvent.CancelOrderItem -> {
                state { copy(isCancelling = true) }
                state.value.currentOrderItem?.id?.let { orderItemId ->
                    viewModelScope.launch {
                        cancelOrderItemUseCase(orderItemId)
                            .onSuccess {
                                state {
                                    copy(
                                        isCancelling = false,
                                        showCancellationDialog = false,
                                        orderItems = orderItems.removeOrderItem(orderItemId),
                                        currentOrderItem = null
                                    )
                                }
                            }
                            .onFailure { state { copy(isCancelling = false) } }
                    }
                }
            }

            is OrderDetailsEvent.EditDeliveryAddress -> {
                state { copy(isEditing = true) }
                state.value.currentOrderItem?.id?.let { orderItemId ->
                    viewModelScope.launch {
                        editDeliveryAddressUseCase(
                            orderItemId = orderItemId,
                            newAddress = event.newAddress
                        )
                            .onSuccess { state { copy(isEditing = false, showEditDialog = false) } }
                    }
                }
            }

            OrderDetailsEvent.HideEditDialog -> {
                state { copy(showEditDialog = false) }
            }

            OrderDetailsEvent.ShowEditDialog -> {
                state { copy(showEditDialog = true) }
            }
        }
    }

    private fun List<OrderItemModel>.removeOrderItem(id: Int) = toMutableList().apply {
        removeIf { it.id == id }
    }
}
