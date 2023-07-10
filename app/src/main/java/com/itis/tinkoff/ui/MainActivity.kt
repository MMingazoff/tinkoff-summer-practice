package com.itis.tinkoff.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.itis.tinkoff.ui.navigation.AppGraph
import com.itis.tinkoff.ui.theme.base.Theme
import com.itis.tinkoff.ui.theme.settings.LocalSettingsEventBus
import com.itis.tinkoff.ui.theme.settings.SettingsEventBus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsEventBus = remember { SettingsEventBus() }
            val currentSettings = settingsEventBus.currentSettings.collectAsState().value

            Theme(
                darkTheme = currentSettings.isDarkMode,
                textSize = currentSettings.textSize,
            ) {
                CompositionLocalProvider(
                    LocalSettingsEventBus provides settingsEventBus
                ) {
                    AppGraph(rememberNavController())
                }
            }
        }
    }
}
