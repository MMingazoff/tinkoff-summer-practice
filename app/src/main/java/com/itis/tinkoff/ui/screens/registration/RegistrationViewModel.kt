package com.itis.tinkoff.ui.screens.registration

import com.itis.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() :
    BaseViewModel<RegistrationState, RegistrationAction, RegistrationEvent>(RegistrationState()) {

    override fun event(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.Register -> {
                state { copy(isLoading = true) }
            }
        }
    }
}
