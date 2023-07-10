package com.itis.tinkoff.ui.navigation

import androidx.annotation.StringRes
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.navigation.SettingsScreen.OrderDetails.getOrderId
import com.itis.tinkoff.ui.screens.settings.balance.BalanceScreen
import com.itis.tinkoff.ui.screens.settings.orderdetails.OrderDetailsScreen
import com.itis.tinkoff.ui.screens.settings.ordershistory.OrdersHistoryScreen

sealed class SettingsScreen(
    @StringRes val name: Int,
    val route: String,
) {

    object Balance : SettingsScreen(
        route = "balance",
        name = R.string.balance,
    )

    object OrdersHistory : SettingsScreen(
        route = "orders",
        name = R.string.orders_history,
    )

    object OrderDetails : SettingsScreen(
        route = "order_details/{orderId}",
        name = R.string.order_details,
    ) {

        fun createRoute(orderId: Int) = "order_details/$orderId"

        val arguments = listOf(navArgument("orderId") { type = NavType.IntType })

        fun NavBackStackEntry.getOrderId() = arguments?.getInt("orderId")
    }
}

fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    composable(SettingsScreen.Balance.route) {
        BalanceScreen(hiltViewModel())
    }
    composable(SettingsScreen.OrdersHistory.route) {
        OrdersHistoryScreen(navController, hiltViewModel())
    }
    composable(SettingsScreen.OrderDetails.route, SettingsScreen.OrderDetails.arguments) {
        OrderDetailsScreen(it.getOrderId(), hiltViewModel())
    }
}

const val settingsGraphRoute = "settings"
