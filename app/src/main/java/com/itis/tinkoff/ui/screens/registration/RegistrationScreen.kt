package com.itis.tinkoff.ui.screens.registration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Surface(color = Theme.colors.primaryBackground) {
        RegistrationContent(state = state.value, eventHandler = viewModel::event)
        RegistrationActions(
            navController = navController,
            action = action
        )
    }
}


@Composable
fun RegistrationContent(
    state: RegistrationState,
    eventHandler: (RegistrationEvent) -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confPassword by rememberSaveable { mutableStateOf("") }
    val selectedSegment = remember { mutableStateOf(User.CUSTOMER) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            CustomTextField(value = email, label = R.string.email_label) {
                email = it
            }
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(value = name, label = R.string.name_label) {
                name = it
            }
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(value = password, label = R.string.password_label) {
                password = it
            }
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(value = confPassword, label = R.string.conf_password_label) {
                confPassword = it
            }
            SegmentPicker(selectedSegment)
        }
        DoneButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            text = R.string.login,
            isLoading = state.isLoading
        ) {
            if (!state.isLoading)
                eventHandler(
                    RegistrationEvent.Register(
                        email, name, password, confPassword, selectedSegment.value
                    )
                )
        }
    }
}

@Composable
fun SegmentPicker(selectedSegment: MutableState<User>) {
    Row(Modifier.padding(8.dp)) {
        SegmentButton(
            text = stringResource(R.string.customer),
            isSelected = selectedSegment.value == User.CUSTOMER,
            onClick = { selectedSegment.value = User.CUSTOMER }
        )
        SegmentButton(
            text = stringResource(R.string.seller),
            isSelected = selectedSegment.value == User.SELLER,
            onClick = { selectedSegment.value = User.SELLER }
        )
    }
}

@Composable
fun SegmentButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Blue else Color.Gray,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text)
    }
}

@Composable
fun RegistrationActions(
    navController: NavController,
    action: RegistrationAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            null -> Unit
            else -> {}
        }
    }
}

enum class User {
    CUSTOMER, SELLER
}
