package com.itis.tinkoff.ui.base

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itis.tinkoff.ui.theme.base.Theme
import com.itis.tinkoff.ui.theme.base.Yellow

@Composable
fun DoneButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            // TODO use theme color
            containerColor = Yellow,
            // TODO use theme color
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(25)
    ) {
        if (isLoading)
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                // TODO use theme color
                color = Color.Black,
                strokeWidth = 2.dp
            )
        else
            Text(text = stringResource(text))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes label: Int,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier.clip(shape = Theme.shapes.cornersStyle),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        label = { Text(stringResource(label)) }
    )
}
