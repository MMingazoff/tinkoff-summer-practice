package com.itis.tinkoff.ui.screens.addproduct

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.base.CustomTextField
import com.itis.tinkoff.ui.base.DoneButton
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Surface(color = Theme.colors.primaryBackground, modifier = Modifier.fillMaxHeight()) {
        Column {
            Toolbar(text = R.string.add_product)
            AddProductContent(
                state = state.value,
                eventHandler = viewModel::event
            )
        }
    }
}

@Composable
private fun AddProductContent(
    state: AddProductState,
    eventHandler: (AddProductEvent) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagePicker(state = state, eventHandler = eventHandler)
            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(value = name, label = R.string.product_name_label) {
                name = it
            }
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                modifier = Modifier.height(80.dp),
                value = description,
                label = R.string.product_description_label,
                singleLine = false,
            ) {
                description = it
            }
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(value = price, label = R.string.product_price_label) {
                price = it
            }

            ProductAddDialog(showDialog = state.showDialog, eventHandler = eventHandler)
        }
        DoneButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            text = R.string.add_product,
            isLoading = state.isAdding
        ) {
            eventHandler(AddProductEvent.AddProduct(name, description, price.toInt()))
        }
    }
}

@Composable
fun ImagePicker(
    state: AddProductState,
    eventHandler: (AddProductEvent) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let { eventHandler(AddProductEvent.SetUri(it)) }
        }


    state.photoUri?.let {
        Image(
            painter = rememberAsyncImagePainter(model = it),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { launcher.launch("image/*") }
        )
    } ?: Button(
        onClick = { launcher.launch("image/*") },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.wrapContentSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.colors.secondaryBackground,
            contentColor = Theme.colors.secondaryText
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Text(text = stringResource(id = R.string.add_photo))
        }
    }
}

@Composable
fun ProductAddDialog(
    showDialog: Boolean,
    eventHandler: (AddProductEvent) -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { eventHandler(AddProductEvent.HideDialog) },
            title = { Text(stringResource(id = R.string.product_add_title)) },
            confirmButton = {
                TextButton(onClick = { eventHandler(AddProductEvent.HideDialog) }) {
                    Text(stringResource(id = android.R.string.ok))
                }
            },
        )
    }
}
