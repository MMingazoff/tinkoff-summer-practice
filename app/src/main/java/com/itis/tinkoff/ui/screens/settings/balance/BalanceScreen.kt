package com.itis.tinkoff.ui.screens.settings.balance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.base.CustomTextField
import com.itis.tinkoff.ui.base.DoneButton
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun BalanceScreen(
    viewModel: BalanceViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Surface(color = Theme.colors.primaryBackground, modifier = Modifier.fillMaxHeight()) {
        Column {
            Toolbar(text = R.string.balance)
            BalanceContent(state = state.value, eventHandler = viewModel::event)
        }
        BalanceActions(action = action)
    }
}

@Composable
private fun BalanceContent(
    state: BalanceState,
    eventHandler: (BalanceEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(top = 56.dp)) {
        Row(
            modifier = Modifier
                .height(46.dp)
        ) {
            var balance by rememberSaveable { mutableStateOf("") }
            // todo: make inputtype numbers
            CustomTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                value = balance,
                label = R.string.amount
            ) {
                balance = it
            }
            DoneButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                text = R.string.top_up,
                isLoading = state.isToppingUp
            ) {
                eventHandler(BalanceEvent.TopUp(balance.toInt()))
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.current_balance),
                    modifier = Modifier.padding(end = 8.dp),
                    style = Theme.typography.heading,
                )
                Text(
                    text = state.balance.toString() + "₿",
                    style = Theme.typography.heading,
                )
            }
        }
    }
}

@Composable
private fun BalanceActions(
    action: BalanceAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            null -> Unit
            else -> {}
        }
    }
}
