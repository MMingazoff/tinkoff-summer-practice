package com.itis.tinkoff.ui.screens.addproduct

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.itis.tinkoff.domain.usecases.AddProductUseCase
import com.itis.tinkoff.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val addProductUseCase: AddProductUseCase,
) : BaseViewModel<AddProductState, AddProductAction, AddProductEvent>(AddProductState()) {

    override fun event(event: AddProductEvent) {
        when (event) {
            is AddProductEvent.SetUri -> {
                state { copy(photoUri = event.uri) }
            }

            is AddProductEvent.AddProduct -> {
                state { copy(isAdding = true) }
                viewModelScope.launch {
                    state.value.photoUri?.let { uri ->
                        addProductUseCase(
                            event.name,
                            event.description,
                            event.price,
                            uri.path ?: "",
                        ).onSuccess { state { copy(isAdding = false, showDialog = true) } }
                    }
                }
            }

            AddProductEvent.HideDialog -> {
                state { copy(showDialog = false) }
            }
        }
    }
}

private fun Uri.prepareFilePart(context: Context): MultipartBody.Part {
    val file = getFileFromUri(context)
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestFile)
}

private fun Uri.getFileFromUri(context: Context): File {
    val contentResolver = context.contentResolver
    val file = File(context.cacheDir, "photo.jpg")
    contentResolver.openInputStream(this)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    return file
}
