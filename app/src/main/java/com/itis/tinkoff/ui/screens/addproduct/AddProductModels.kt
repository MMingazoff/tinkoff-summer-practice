package com.itis.tinkoff.ui.screens.addproduct

import android.net.Uri


sealed interface AddProductAction {

    object ShowDialog : AddProductAction
}

sealed interface AddProductEvent {

    class SetUri(val uri: Uri) : AddProductEvent

    object HideDialog : AddProductEvent

    class AddProduct(val name: String, val description: String, val price: Int) : AddProductEvent
}
