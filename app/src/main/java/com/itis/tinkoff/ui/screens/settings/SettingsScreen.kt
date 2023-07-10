package com.itis.tinkoff.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.navigation.SettingsScreen
import com.itis.tinkoff.ui.theme.base.Theme
import com.itis.tinkoff.ui.theme.settings.LocalSettingsEventBus

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val settingsEventBus = LocalSettingsEventBus.current
    val currentSettings = settingsEventBus.currentSettings.collectAsState().value

    Surface(color = Theme.colors.primaryBackground, modifier = Modifier.fillMaxSize()) {
        Box {
            Toolbar(text = R.string.settings, modifier = Modifier.align(Alignment.TopCenter))
            Column(modifier = Modifier.align(Alignment.Center)) {
                Setting(icon = Icons.Filled.AccountCircle, text = R.string.account) {

                }
                Spacer(modifier = Modifier.height(8.dp))
                Setting(icon = Icons.Filled.Refresh, text = R.string.orders_history) {
                    navController.navigate(SettingsScreen.OrdersHistory.route)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Setting(icon = Icons.Filled.AddCircle, text = R.string.balance) {
                    navController.navigate(SettingsScreen.Balance.route)
                }
                ThemeSwitcher(isDarkTheme = currentSettings.isDarkMode) { isDarkTheme ->
                    settingsEventBus.updateDarkMode(isDarkTheme)
                }
            }
        }
    }
}

@Composable
private fun Setting(
    icon: ImageVector,
    @StringRes text: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 8.dp),
            tint = Theme.colors.primaryText,
        )
        Text(text = stringResource(id = text), style = Theme.typography.body)
        Box(modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier.align(Alignment.CenterEnd),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Theme.colors.primaryText,
            )
        }
    }
}

@Composable
private fun ThemeSwitcher(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 8.dp),
            tint = Theme.colors.primaryText,
        )
        Text(text = stringResource(id = R.string.dark_theme), style = Theme.typography.body)
        Box(modifier = Modifier.fillMaxWidth()) {
            Switch(
                modifier = Modifier.align(Alignment.CenterEnd),
                checked = isDarkTheme,
                onCheckedChange = onThemeChange,
            )
        }
    }
}
