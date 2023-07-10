package com.itis.tinkoff.ui.screens.auth.registration

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itis.tinkoff.R
import com.itis.tinkoff.domain.models.User
import com.itis.tinkoff.ui.base.CustomTextField
import com.itis.tinkoff.ui.base.DoneButton
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.navigation.mainGraphRoute
import com.itis.tinkoff.ui.navigation.popUpToTop
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Surface(color = Theme.colors.primaryBackground) {
        Column {
            Toolbar(text = R.string.registration)
            RegistrationContent(state = state.value, eventHandler = viewModel::event)
        }
        RegistrationActions(
            navController = navController,
            action = action
        )
    }
}


@Composable
private fun RegistrationContent(
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
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Spacer(modifier = Modifier.height(16.dp))
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
private fun SegmentPicker(selectedSegment: MutableState<User>) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Theme.colors.secondaryBackground)
            .padding(8.dp)
    ) {
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
private fun SegmentButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .width(144.dp)
            .clickable { onClick() },
        elevation = 16.dp,
        backgroundColor = if (isSelected)
            Theme.colors.primaryBackground
        else
            Theme.colors.secondaryBackground,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
private fun RegistrationActions(
    navController: NavController,
    action: RegistrationAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            null -> Unit
            RegistrationAction.Navigate -> {
                navController.navigate(mainGraphRoute) {
                    popUpToTop(navController)
                }
            }
        }
    }
}
