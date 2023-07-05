package com.itis.tinkoff.ui.theme.settings

import com.itis.tinkoff.ui.theme.base.Corners
import com.itis.tinkoff.ui.theme.base.Size
import com.itis.tinkoff.ui.theme.base.Style

data class CurrentSettings(
    val isDarkMode: Boolean,
    val textSize: Size,
    val paddingSize: Size,
    val cornerStyle: Corners,
    val style: Style,
)
