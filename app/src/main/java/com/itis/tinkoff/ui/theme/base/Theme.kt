package com.itis.tinkoff.ui.theme.base

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Composable
fun Theme(
    textSize: Size = Size.Medium,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme)
        darkPalette
    else
        lightPalette

    val typography = Typography(
        heading = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 24.sp
                Size.Medium -> 28.sp
                Size.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = colors.primaryText,
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 14.sp
                Size.Medium -> 16.sp
                Size.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal,
            color = colors.primaryText,
        ),
        toolbar = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = when (textSize) {
                Size.Small -> 22.sp
                Size.Medium -> 24.sp
                Size.Big -> 26.sp
            },
            fontWeight = FontWeight.Black,
            color = colors.primaryText,
        ),
        caption = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 10.sp
                Size.Medium -> 12.sp
                Size.Big -> 14.sp
            },
            color = colors.primaryText,
        )
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.toolbarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        content = content
    )
}
