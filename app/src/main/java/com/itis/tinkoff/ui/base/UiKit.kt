package com.itis.tinkoff.ui.base

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
        if (isLoading)
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = Theme.colors.primaryText,
                strokeWidth = 2.dp
            )
        else
            Text(text = stringResource(text))
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes label: Int,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
) {
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
        ),
        label = { Text(stringResource(label)) },
        singleLine = singleLine,
    )
}

@Composable
fun Toolbar(@StringRes text: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Theme.colors.toolbarColor)
    ) {
        Text(
            text = stringResource(id = text),
            style = Theme.typography.toolbar,
            modifier = Modifier.padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxHeight()) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Bottom)
            )
        }
    }
}

@Composable
fun cachingImage(url: String?) = ImageRequest.Builder(LocalContext.current)
    .data(url)
    .crossfade(true)
    .memoryCachePolicy(CachePolicy.ENABLED)
    .diskCachePolicy(CachePolicy.ENABLED)
    .build()
