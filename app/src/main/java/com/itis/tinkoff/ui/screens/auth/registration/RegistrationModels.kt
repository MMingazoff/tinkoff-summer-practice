package com.itis.tinkoff.ui.screens.auth.registration

import com.itis.tinkoff.domain.models.User

sealed interface RegistrationAction {

    object Navigate : RegistrationAction
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
