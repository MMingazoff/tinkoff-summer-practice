package com.itis.tinkoff.ui.screens.cart.orderconfirmation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itis.tinkoff.R
import com.itis.tinkoff.domain.models.CartProductModel
import com.itis.tinkoff.ui.base.CustomTextField
import com.itis.tinkoff.ui.base.DoneButton
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.base.cachingImage
import com.itis.tinkoff.ui.navigation.SettingsScreen
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun ConfirmationScreen(
    navController: NavController,
    viewModel: ConfirmationViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Surface(color = Theme.colors.primaryBackground, modifier = Modifier.fillMaxSize()) {
        Column {
            Toolbar(text = R.string.order_confirmation)
            ConfirmationContent(state = state.value, eventHandler = viewModel::event)
        }
        ConfirmationActions(
            navController = navController, action = action
        )
    }
}

@Composable
private fun ConfirmationContent(
    state: ConfirmationState,
    eventHandler: (ConfirmationEvent) -> Unit,
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
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn {
                    items(state.cartProducts, key = { it.id }) { product ->
                        CartProduct(product = product)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                    Receiver(state = state)
                    Spacer(modifier = Modifier.height(8.dp))
                    var deliveryAddress by rememberSaveable { mutableStateOf("") }
                    CustomTextField(
                        value = deliveryAddress,
                        label = R.string.delivery_address,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        deliveryAddress = it
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OrderPricing(state = state)
                    Spacer(modifier = Modifier.height(8.dp))
                    DoneButton(text = R.string.pay, isLoading = state.isPlacingOrder) {
                        eventHandler(ConfirmationEvent.PayOrder(deliveryAddress))
                    }
                }
            }
        }
    }
}

@Composable
private fun Receiver(state: ConfirmationState) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = null,
                tint = Theme.colors.primaryText,
                modifier = Modifier.size(40.dp)
            )
            Column {
                state.profile?.let {
                    Text(
                        text = it.name,
                        color = Theme.colors.primaryText,
                        style = Theme.typography.body
                    )
                    Text(
                        text = it.email,
                        color = Theme.colors.primaryText,
                        style = Theme.typography.caption
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderPricing(state: ConfirmationState) {
    Column {
        Text(
            text = stringResource(id = R.string.order_sum),
            color = Theme.colors.primaryText,
            style = Theme.typography.heading
        )
        val productsPrice = state.cartProducts.sumOf { it.product.price * it.quantity }
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.products),
                    color = Theme.colors.primaryText,
                    style = Theme.typography.caption,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = "$productsPrice ₿",
                    color = Theme.colors.primaryText,
                    style = Theme.typography.caption,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.delivery),
                    color = Theme.colors.primaryText,
                    style = Theme.typography.caption,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = "100 ₿",
                    color = Theme.colors.primaryText,
                    style = Theme.typography.caption,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.total),
                color = Theme.colors.primaryText,
                style = Theme.typography.heading,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = (productsPrice + 100).toString() + " ₿",
                color = Theme.colors.primaryText,
                style = Theme.typography.heading,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
private fun CartProduct(product: CartProductModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(100.dp),
        elevation = 8.dp,
        backgroundColor = Theme.colors.secondaryBackground,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = cachingImage(url = product.product.photo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(8.dp)),
            )
            Column(
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = product.product.name,
                    color = Theme.colors.primaryText,
                    style = Theme.typography.body
                )
                Text(
                    text = product.product.price.toString(),
                    color = Theme.colors.primaryText,
                    style = Theme.typography.caption
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = "x" + product.quantity.toString(),
                    style = Theme.typography.heading
                )
            }
        }
    }
}

@Composable
private fun ConfirmationActions(
    navController: NavController,
    action: ConfirmationAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            null -> Unit
            is ConfirmationAction.Navigate -> {
                navController.popBackStack()
                navController.navigate(SettingsScreen.OrderDetails.createRoute(action.orderId))
            }
        }
    }
}
