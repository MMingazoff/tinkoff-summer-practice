package com.itis.tinkoff.ui.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.base.CustomTextField
import com.itis.tinkoff.ui.base.DoneButton
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.navigation.AuthScreen
import com.itis.tinkoff.ui.navigation.mainGraphRoute
import com.itis.tinkoff.ui.navigation.popUpToTop
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Surface(color = Theme.colors.primaryBackground) {
        Column {
            Toolbar(text = R.string.login)
            LoginContent(
                navController = navController,
                state = state.value,
                eventHandler = viewModel::event
            )
        }
        LoginActions(
            navController = navController,
            action = action
        )
    }
}

@Composable
private fun LoginContent(
    navController: NavController,
    state: LoginState,
    eventHandler: (LoginEvent) -> Unit,
) {
    var username by rememberSaveable { mutableStateOf("") }
    var showUsernameError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var showPasswordError by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                value = username,
                label = R.string.username_label,
                showError = showUsernameError,
            ) {
                username = it
                if (showUsernameError) showUsernameError = false
            }
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = password,
                label = R.string.password_label,
                showError = showPasswordError,
                isPassword = true,
            ) {
                password = it
                if (showPasswordError) showPasswordError = false
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = stringResource(id = R.string.no_account))
                Spacer(modifier = Modifier.width(4.dp))
                // TODO use theme color
                Text(
                    text = stringResource(id = R.string.register),
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        navController.navigate(AuthScreen.SignUp.route)
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (state.showError)
                Text(
                    text = stringResource(id = R.string.login_error),
                    style = Theme.typography.caption,
                    color = Theme.colors.errorColor
                )
        }
        DoneButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            text = R.string.login,
            isLoading = state.isLoading
        ) {
            if (!state.isLoading) {
                if (username.isEmpty()) showUsernameError = true
                if (password.isEmpty()) showPasswordError = true

                if (username.isNotEmpty() && password.isNotEmpty())
                    eventHandler(LoginEvent.LogIn(username, password))
            }
        }
    }
}

@Composable
private fun LoginActions(
    navController: NavController,
    action: LoginAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            is LoginAction.Navigate -> {
                localUserRole = action.role
                navController.navigate(mainGraphRoute) {
                    popUpToTop(navController)
                }
            }

            null -> Unit
        }
    }
}
