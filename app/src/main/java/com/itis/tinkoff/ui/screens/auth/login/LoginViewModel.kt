package com.itis.tinkoff.ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import com.itis.android.ui.base.BaseViewModel
import com.itis.tinkoff.domain.usecases.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
) : BaseViewModel<LoginState, LoginAction, LoginEvent>(LoginState()) {

    override fun event(event: LoginEvent) {
        when (event) {
            is LoginEvent.LogIn -> {
                state { copy(isLoading = true) }
                viewModelScope.launch {
                    logInUseCase(event.username, event.password)
                        .onSuccess {
                            state { copy(isLoading = false) }
                            action { LoginAction.Navigate(role = it) }
                        }
                }
            }
        }
    }
}
