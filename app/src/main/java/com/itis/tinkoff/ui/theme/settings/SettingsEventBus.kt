package com.itis.tinkoff.ui.theme.settings

import androidx.compose.runtime.staticCompositionLocalOf
import com.itis.tinkoff.ui.theme.base.Size
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsEventBus {

    private val _currentSettings: MutableStateFlow<CurrentSettings> = MutableStateFlow(
        CurrentSettings(
            isDarkMode = false,
            textSize = Size.Medium,
        )
    )
    val currentSettings: StateFlow<CurrentSettings> = _currentSettings

    fun updateDarkMode(isDarkMode: Boolean) {
        _currentSettings.value = _currentSettings.value.copy(isDarkMode = isDarkMode)
    }
}

val LocalSettingsEventBus = staticCompositionLocalOf {
    SettingsEventBus()
}
