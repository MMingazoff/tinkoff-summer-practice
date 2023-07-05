package com.itis.tinkoff.ui.screens.login

sealed interface LoginAction {

    object Navigate : LoginAction
}

sealed interface LoginEvent {

    class LogIn(username: String, password: String) : LoginEvent
}
