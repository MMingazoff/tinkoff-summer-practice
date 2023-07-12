package com.itis.tinkoff.ui.base

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun DoneButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    isLoading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Theme.colors.tintColor,
        contentColor = Theme.colors.onTintColor,
    ),
    onClick: () -> Unit,
) {
    Button(
        onClick = { if (!isLoading) onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = colors,
        shape = RoundedCornerShape(25)
    ) {
        if (isLoading) CircularProgressIndicator(
            modifier = Modifier.size(16.dp), color = Theme.colors.primaryText, strokeWidth = 2.dp
        )
        else Text(text = stringResource(text))
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes label: Int,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isPassword: Boolean = false,
    showError: Boolean = false,
    errorMessage: String = "",
    onValueChange: (String) -> Unit,
) {
    var showPassword by rememberSaveable { mutableStateOf(!isPassword) }
    TextField(
        modifier = modifier.clip(shape = RoundedCornerShape(16.dp)),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Theme.colors.secondaryBackground,
            unfocusedContainerColor = Theme.colors.secondaryBackground,
            focusedTextColor = Theme.colors.primaryText,
            unfocusedTextColor = Theme.colors.primaryText,
            disabledTextColor = Theme.colors.primaryText,
        ),
        isError = showError,
        label = { Text(stringResource(label)) },
        trailingIcon = {
            if (showError) Icon(
                Icons.Filled.Error, null, tint = Theme.colors.errorColor
            )
            else if (isPassword) Icon(
                imageVector = if (showPassword) Icons.Filled.RemoveRedEye
                else Icons.Outlined.RemoveRedEye,
                contentDescription = null,
                tint = Theme.colors.primaryText,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .clickable { showPassword = !showPassword }
            )
        },
        visualTransformation = if (showPassword) VisualTransformation.None
        else PasswordVisualTransformation(),
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
    )
}

@Composable
fun Toolbar(@StringRes text: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Theme.colors.toolbarColor)
    ) {
        Text(
            text = stringResource(id = text),
            style = Theme.typography.toolbar,
            modifier = Modifier.padding(start = 24.dp, top = 4.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
    }
}

@Composable
fun cachingImage(url: String?) =
    ImageRequest.Builder(LocalContext.current).data(url).crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED).diskCachePolicy(CachePolicy.ENABLED).build()
