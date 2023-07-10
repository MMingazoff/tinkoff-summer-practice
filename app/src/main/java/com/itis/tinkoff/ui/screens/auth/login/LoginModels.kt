package com.itis.tinkoff.ui.screens.auth.login

import com.itis.tinkoff.domain.models.User

sealed interface LoginAction {

    class Navigate(val role: User) : LoginAction
}

sealed interface LoginEvent {

    class LogIn(val username: String, val password: String) : LoginEvent
}

var localUserRole = User.CUSTOMER
