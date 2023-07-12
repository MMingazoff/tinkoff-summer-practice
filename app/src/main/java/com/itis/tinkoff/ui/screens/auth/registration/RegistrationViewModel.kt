package com.itis.tinkoff.ui.screens.auth.registration

import androidx.lifecycle.viewModelScope
import com.itis.tinkoff.domain.usecases.RegisterUseCase
import com.itis.tinkoff.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel<RegistrationState, RegistrationAction, RegistrationEvent>(RegistrationState()) {

    override fun event(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.Register -> {
                state { copy(isLoading = true) }
                viewModelScope.launch {
                    registerUseCase(event.email, event.name, event.password, event.user)
                        .onSuccess { action { RegistrationAction.Navigate } }
                }
            }
        }
    }
}
