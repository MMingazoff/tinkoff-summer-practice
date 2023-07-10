package com.itis.tinkoff.ui.screens.addproduct

import android.net.Uri

data class AddProductState(
    val isAdding: Boolean = false,
    val photoUri: Uri? = null,
    val showDialog: Boolean = false,
)
