package com.itis.tinkoff.ui.navigation

import androidx.annotation.StringRes
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.screens.auth.login.LoginScreen
import com.itis.tinkoff.ui.screens.auth.registration.RegistrationScreen

sealed class AuthScreen(
    @StringRes val name: Int,
    val route: String,
) {

    object SignIn : AuthScreen(
        route = "login",
        name = R.string.login,
    )

    object SignUp : AuthScreen(
        route = "registration",
        name = R.string.registration,
    )
}

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(startDestination = AuthScreen.SignIn.route, route = authGraphRoute) {
        composable(AuthScreen.SignIn.route) { LoginScreen(navController, hiltViewModel()) }
        composable(AuthScreen.SignUp.route) { RegistrationScreen(navController, hiltViewModel()) }
    }
}

const val authGraphRoute = "auth"
