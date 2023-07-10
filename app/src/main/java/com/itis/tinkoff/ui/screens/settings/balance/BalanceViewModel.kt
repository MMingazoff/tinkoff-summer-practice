package com.itis.tinkoff.ui.screens.settings.balance

import androidx.lifecycle.viewModelScope
import com.itis.android.ui.base.BaseViewModel
import com.itis.tinkoff.domain.usecases.GetBalanceUseCase
import com.itis.tinkoff.domain.usecases.TopUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val topUpUseCase: TopUpUseCase,
) : BaseViewModel<BalanceState, BalanceAction, BalanceEvent>(BalanceState()) {

    init {
        event(BalanceEvent.GetBalance)
    }

    override fun event(event: BalanceEvent) {
        when (event) {
            is BalanceEvent.TopUp -> {
                state { copy(isToppingUp = true) }
                viewModelScope.launch {
                    topUpUseCase(event.amount)
                        .onSuccess {
                            state {
                                copy(isToppingUp = false, balance = balance + event.amount)
                            }
                        }
                        .onFailure { }
                }
            }

            BalanceEvent.GetBalance -> {
                state { copy(isLoading = true) }
                viewModelScope.launch {
                    getBalanceUseCase()
                        .onSuccess { state { copy(isLoading = false, balance = it) } }
                        .onFailure { }
                }
            }
        }
    }
}
