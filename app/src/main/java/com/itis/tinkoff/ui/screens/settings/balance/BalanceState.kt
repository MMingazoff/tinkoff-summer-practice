package com.itis.tinkoff.ui.screens.settings.balance

data class BalanceState(
    val isLoading: Boolean = false,
    val isToppingUp: Boolean = false,
    val balance: Int = 0,
)
