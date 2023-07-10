package com.itis.tinkoff.ui.screens.settings.balance

sealed interface BalanceAction {

}

sealed interface BalanceEvent {

    class TopUp(val amount: Int) : BalanceEvent

    object GetBalance : BalanceEvent
}
