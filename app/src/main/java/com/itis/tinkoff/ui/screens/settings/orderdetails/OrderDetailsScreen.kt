package com.itis.tinkoff.ui.screens.settings.orderdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.itis.tinkoff.R
import com.itis.tinkoff.domain.models.OrderItemModel
import com.itis.tinkoff.domain.models.OrderStatus
import com.itis.tinkoff.ui.base.CustomTextField
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.base.cachingImage
import com.itis.tinkoff.ui.theme.base.Theme
import kotlinx.coroutines.launch

@Composable
fun OrderDetailsScreen(
    orderId: Int?,
    viewModel: OrderDetailsViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    viewModel.event(OrderDetailsEvent.LoadOrder(orderId))

    Surface(color = Theme.colors.primaryBackground) {
        Column {
            Toolbar(text = R.string.order_details)
            OrderItemBottomSheet(state = state.value, eventHandler = viewModel::event)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OrderItemBottomSheet(
    state: OrderDetailsState,
    eventHandler: (OrderDetailsEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            OrderItemDetailsContent(state = state, eventHandler = eventHandler)
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        OrderDetailsContent(state, eventHandler, sheetState)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OrderDetailsContent(
    state: OrderDetailsState,
    eventHandler: (OrderDetailsEvent) -> Unit,
    bottomSheetState: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()
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
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(state.orderItems, key = { it.id }) { orderItem ->
                        OrderItem(orderItem = orderItem) {
                            eventHandler(OrderDetailsEvent.SelectOrderItem(orderItem))
                            scope.launch {
                                bottomSheetState.show()
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderItem(orderItem: OrderItemModel, onClick: (() -> Unit)? = null) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .clickable(enabled = onClick != null) { onClick?.invoke() }
        .height(100.dp),
        elevation = 8.dp,
        backgroundColor = Theme.colors.secondaryBackground,
        shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = cachingImage(url = orderItem.product.photo),
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
                    text = orderItem.product.name,
                    color = Theme.colors.primaryText,
                    style = Theme.typography.body
                )
                Text(
                    text = orderItem.product.price.toString(),
                    color = Theme.colors.primaryText,
                    style = Theme.typography.caption
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "x" + orderItem.quantity.toString(), style = Theme.typography.heading)
                if (onClick != null) Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Theme.colors.primaryText
                )
            }
        }
    }
}

@Composable
private fun OrderItemDetailsContent(
    state: OrderDetailsState,
    eventHandler: (OrderDetailsEvent) -> Unit,
) {
    state.currentOrderItem?.let { orderItem ->
        Surface(color = Theme.colors.primaryBackground) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(id = R.string.status), style = Theme.typography.heading)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Status(status = OrderStatus.New, currentStatus = orderItem.status)
                Status(status = OrderStatus.InDelivery, currentStatus = orderItem.status)
                Status(status = OrderStatus.Received, currentStatus = orderItem.status)
                Status(status = OrderStatus.Cancelled, currentStatus = orderItem.status)
                Spacer(modifier = Modifier.height(8.dp))
                state.date?.let {
                    Text(
                        text = stringResource(id = R.string.order_date) + it.toString(),
                        modifier = Modifier.padding(start = 8.dp),
                        style = Theme.typography.body
                    )
                }
                OrderItem(orderItem = orderItem)
                EditDeliveryAddressDialog(
                    showDialog = state.showEditDialog,
                    isEditing = state.isEditing,
                    eventHandler = eventHandler
                ) {
                    eventHandler(OrderDetailsEvent.HideEditDialog)
                }
                TextButton(onClick = { eventHandler(OrderDetailsEvent.ShowEditDialog) }) {
                    Text(
                        text = stringResource(id = R.string.edit_delivery_address),
                        style = Theme.typography.body
                    )
                }
                OrderCancelDialog(
                    showDialog = state.showCancellationDialog,
                    isCancelling = state.isCancelling,
                    eventHandler = eventHandler
                ) {
                    eventHandler(OrderDetailsEvent.HideCancellationDialog)
                }
                TextButton(onClick = { eventHandler(OrderDetailsEvent.ShowCancellationDialog) }) {
                    Text(
                        text = stringResource(id = R.string.cancel_order_item),
                        style = Theme.typography.body
                    )
                }
            }
        }
    }
}

@Composable
fun OrderCancelDialog(
    showDialog: Boolean,
    isCancelling: Boolean,
    eventHandler: (OrderDetailsEvent) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    stringResource(id = R.string.order_cancel_alert_title),
                    color = Theme.colors.primaryText
                )
            },
            text = {
                Text(
                    stringResource(id = R.string.order_cancel_alert),
                    color = Theme.colors.primaryText
                )
            },
            confirmButton = {
                if (isCancelling)
                    Row(horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Theme.colors.primaryText,
                            strokeWidth = 2.dp,
                        )
                    }
                else
                    TextButton(onClick = { eventHandler(OrderDetailsEvent.CancelOrderItem) }) {
                        Text(stringResource(id = android.R.string.ok))
                    }
            },
            dismissButton = {
                if (!isCancelling)
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(id = android.R.string.cancel))
                    }
            },
            containerColor = Theme.colors.primaryBackground,
        )
    }
}

@Composable
fun EditDeliveryAddressDialog(
    showDialog: Boolean,
    isEditing: Boolean,
    eventHandler: (OrderDetailsEvent) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        var address by rememberSaveable { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    stringResource(id = R.string.edit_delivery_address),
                    color = Theme.colors.primaryText
                )
            },
            text = {
                CustomTextField(value = address, label = R.string.enter_new_address) {
                    address = it
                }
            },
            confirmButton = {
                if (isEditing)
                    Row(horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Theme.colors.primaryText,
                            strokeWidth = 2.dp,
                        )
                    }
                else
                    TextButton(onClick = {
                        eventHandler(OrderDetailsEvent.EditDeliveryAddress(address))
                    }) {
                        Text(stringResource(id = android.R.string.ok))
                    }
            },
            dismissButton = {
                if (!isEditing)
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(id = android.R.string.cancel))
                    }
            },
            containerColor = Theme.colors.primaryBackground,
        )
    }
}

@Composable
private fun Status(status: OrderStatus, currentStatus: OrderStatus) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = Theme.colors.primaryText
        )
        Text(
            text = stringResource(id = status.stringRes),
            style = if (status == currentStatus) Theme.typography.heading
            else Theme.typography.caption
        )
    }
}
