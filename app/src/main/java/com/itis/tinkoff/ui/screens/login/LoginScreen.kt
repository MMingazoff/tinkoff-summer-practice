package com.itis.tinkoff.ui.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Surface(color = Theme.colors.primaryBackground) {
        LoginContent(state = state.value, eventHandler = viewModel::event)
        LoginActions(
            navController = navController,
            action = action
        )
    }
}

@Composable
fun LoginContent(
    state: LoginState,
    eventHandler: (LoginEvent) -> Unit,
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            CustomTextField(value = username, label = R.string.username_label) {
                username = it
            }
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(value = password, label = R.string.password_label) {
                password = it
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = stringResource(id = R.string.no_account))
                Spacer(modifier = Modifier.width(4.dp))
                // TODO use theme color
                Text(
                    text = stringResource(id = R.string.register),
                    color = Color.Blue,
                    modifier = Modifier.clickable { /* nav to register*/ }
                )
            }
        }
        DoneButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            text = R.string.login,
            isLoading = state.isLoading
        ) {
            if (!state.isLoading)
                eventHandler(LoginEvent.LogIn(username, password))
        }
    }
}

@Composable
fun LoginActions(
    navController: NavController,
    action: LoginAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            LoginAction.Navigate -> TODO()

            null -> Unit
        }
    }
}
