package com.itis.tinkoff.ui.screens.home.productdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.base.cachingImage
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun DetailsScreen(
    productId: Int?,
    viewModel: DetailsViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    productId?.let { viewModel.event(DetailsEvent.LoadProduct(it)) }

    Surface(color = Theme.colors.primaryBackground, modifier = Modifier.fillMaxHeight()) {
        Column {
            Toolbar(text = R.string.order_details)
            DetailsContent(state = state.value, eventHandler = viewModel::event)
        }
    }
}

@Composable
private fun DetailsContent(
    state: DetailsState,
    eventHandler: (DetailsEvent) -> Unit,
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = cachingImage(url = state.product?.photo),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            if (state.product != null) {
                Text(
                    text = state.product.name,
                    color = Theme.colors.primaryText,
                    style = Theme.typography.heading
                )
                Text(
                    text = state.product.description,
                    color = Theme.colors.primaryText,
                    style = Theme.typography.body
                )

            }
            Box(modifier = Modifier.fillMaxSize()) {
                state.product?.price?.let {
                    Text(
                        text = it.toString(),
                        color = Theme.colors.primaryText,
                        style = Theme.typography.heading,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 8.dp, bottom = 4.dp)
                    )
                }
                Button(
                    onClick = { eventHandler(DetailsEvent.AddToCart) },
                    modifier = Modifier.align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Theme.colors.tintColor,
                        contentColor = Theme.colors.primaryText
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_cart),
                        color = Theme.colors.onTintColor
                    )
                }
            }
        }
    }
}
