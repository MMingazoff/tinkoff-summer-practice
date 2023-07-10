package com.itis.tinkoff.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.itis.tinkoff.R
import com.itis.tinkoff.domain.models.User
import com.itis.tinkoff.ui.screens.addproduct.AddProductScreen
import com.itis.tinkoff.ui.screens.auth.login.localUserRole
import com.itis.tinkoff.ui.screens.cart.cart.CartScreen
import com.itis.tinkoff.ui.screens.home.home.HomeScreen
import com.itis.tinkoff.ui.screens.settings.SettingsScreen
import com.itis.tinkoff.ui.theme.base.Theme

sealed class MainScreen(
    val icon: ImageVector,
    @StringRes val name: Int,
    val route: String,
) {

    object Home : MainScreen(
        route = homeGraphRoute,
        name = R.string.home,
        icon = Icons.Filled.Home,
    )

    object Cart : MainScreen(
        route = cartGraphRoute,
        name = R.string.cart,
        icon = Icons.Filled.ShoppingCart,
    )

    object AddProduct : MainScreen(
        route = "add_product",
        name = R.string.add_product,
        icon = Icons.Filled.AddCircle,
    )

    object Settings : MainScreen(
        route = settingsGraphRoute,
        name = R.string.settings,
        icon = Icons.Filled.Settings,
    )
}

@Composable
fun MainGraph(
    appNavController: NavHostController,
    startDestination: MainScreen = MainScreen.Home
) {
    val isUserCustomer = localUserRole == User.CUSTOMER
    val navController = rememberNavController()
    val items = listOf(
        MainScreen.Home,
        if (isUserCustomer) MainScreen.Cart else MainScreen.AddProduct,
        MainScreen.Settings,
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Theme.colors.secondaryBackground
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null,
                                tint = Theme.colors.primaryText
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(screen.name),
                                color = Theme.colors.primaryText
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MainScreen.Home.route) { HomeScreen(appNavController, hiltViewModel()) }
            if (isUserCustomer)
                composable(MainScreen.Cart.route) { CartScreen(appNavController, hiltViewModel()) }
            else
                composable(MainScreen.AddProduct.route) {
                    AddProductScreen(hiltViewModel())
                }
            composable(MainScreen.Settings.route) { SettingsScreen(appNavController) }
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable(mainGraphRoute) { MainGraph(navController) }
    homeGraph()
    cartGraph(navController)
    settingsGraph(navController)
}

const val mainGraphRoute = "main"
