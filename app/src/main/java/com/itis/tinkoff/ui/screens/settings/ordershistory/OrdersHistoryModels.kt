package com.itis.tinkoff.ui.screens.settings.ordershistory

sealed interface OrdersHistoryAction {

}

sealed interface OrdersHistoryEvent {

    object LoadOrders : OrdersHistoryEvent
}
