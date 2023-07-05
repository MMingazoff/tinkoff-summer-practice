package com.itis.tinkoff.ui.screens.registration

sealed interface RegistrationAction {
}

sealed interface RegistrationEvent {
    class Register(
        val email: String,
        val name: String,
        val password: String,
        val confPassword: String,
        val user: User,
    ) : RegistrationEvent
}
