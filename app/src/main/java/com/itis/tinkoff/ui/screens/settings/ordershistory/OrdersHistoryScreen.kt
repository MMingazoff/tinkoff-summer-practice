package com.itis.tinkoff.ui.screens.settings.ordershistory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itis.tinkoff.R
import com.itis.tinkoff.domain.models.OrderModel
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.navigation.SettingsScreen
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun OrdersHistoryScreen(
    navController: NavController,
    viewModel: OrdersHistoryViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Surface(color = Theme.colors.primaryBackground, modifier = Modifier.fillMaxHeight()) {
        Column {
            Toolbar(text = R.string.orders_history)
            OrdersHistoryContent(
                navController = navController,
                state = state.value,
            )
        }
    }
}

@Composable
private fun OrdersHistoryContent(
    navController: NavController,
    state: OrdersHistoryState,
) {
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                color = Theme.colors.primaryText
            )
        }
    } else {
        LazyColumn {
            items(state.orders, key = { it.id }) { order ->
                Order(order = order) {
                    navController.navigate(SettingsScreen.OrderDetails.createRoute(order.id))
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun Order(order: OrderModel, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(8.dp)
        .height(100.dp),
        elevation = 8.dp,
        backgroundColor = Theme.colors.secondaryBackground,
        shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier
                .height(56.dp)
                .padding(8.dp),
        ) {
            Text(
                text = order.orderItems.size.toString(),
                color = Theme.colors.primaryText,
                style = Theme.typography.body
            )
            Text(
                text = order.date.toString(),
                color = Theme.colors.primaryText,
                style = Theme.typography.caption
            )
        }
    }
}
