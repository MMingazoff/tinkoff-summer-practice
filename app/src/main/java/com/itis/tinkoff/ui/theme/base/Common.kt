package com.itis.tinkoff.ui.theme.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class Colors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val onTintColor: Color,
    val controlColor: Color,
    val errorColor: Color,
    val toolbarColor: Color,
)

data class Typography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

object Theme {

    val colors: Colors
        @Composable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current
}

enum class Size {
    Small, Medium, Big
}

val LocalColors = staticCompositionLocalOf<Colors> {
    error("No colors provided")
}

val LocalTypography = staticCompositionLocalOf<Typography> {
    error("No font provided")
}
