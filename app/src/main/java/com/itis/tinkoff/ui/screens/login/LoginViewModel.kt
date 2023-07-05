package com.itis.tinkoff.ui.screens.login

import com.itis.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginState, LoginAction, LoginEvent>(LoginState()) {

    override fun event(event: LoginEvent) {
        when (event) {
            is LoginEvent.LogIn -> {
                state { copy(isLoading = true) }
            }
        }
    }
}
