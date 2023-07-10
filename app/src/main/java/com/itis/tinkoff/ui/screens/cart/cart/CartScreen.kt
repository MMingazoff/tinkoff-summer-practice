package com.itis.tinkoff.ui.screens.cart.cart

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itis.tinkoff.R
import com.itis.tinkoff.domain.models.CartProductModel
import com.itis.tinkoff.ui.base.DoneButton
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.base.cachingImage
import com.itis.tinkoff.ui.navigation.CartScreen
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Surface(color = Theme.colors.primaryBackground, modifier = Modifier.fillMaxHeight()) {
        Column {
            Toolbar(text = R.string.cart)
            CartContent(
                navController = navController,
                state = state.value,
                eventHandler = viewModel::event
            )
        }
    }
}

@Composable
private fun CartContent(
    navController: NavController,
    state: CartState,
    eventHandler: (CartEvent) -> Unit,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.cartProducts, key = { it.id }) { product ->
                        CartProduct(product = product, eventHandler)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
            DoneButton(
                text = R.string.place_order,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {
                navController.navigate(CartScreen.OrderConfirmation.route)
            }
        }
    }
}

@Composable
private fun CartProduct(
    product: CartProductModel,
    eventHandler: (CartEvent) -> Unit,
) {
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
                model = cachingImage(url = product.photo),
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
                    text = product.name,
                    color = Theme.colors.primaryText,
                    style = Theme.typography.body
                )
                Text(
                    text = product.price.toString(),
                    color = Theme.colors.primaryText,
                    style = Theme.typography.caption
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                eventHandler(CartEvent.DecreaseProductAmount(product.id))
                            },
                        tint = Theme.colors.primaryText
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = product.quantity.toString(), style = Theme.typography.heading)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                eventHandler(CartEvent.IncreaseProductAmount(product.id))
                            },
                        tint = Theme.colors.primaryText
                    )
                }
            }
        }
    }
}
