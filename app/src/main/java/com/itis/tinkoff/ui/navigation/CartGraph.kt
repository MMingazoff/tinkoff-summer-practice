package com.itis.tinkoff.ui.navigation

import androidx.annotation.StringRes
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.navigation.SettingsScreen.OrderDetails.getOrderId
import com.itis.tinkoff.ui.screens.cart.orderconfirmation.ConfirmationScreen
import com.itis.tinkoff.ui.screens.settings.orderdetails.OrderDetailsScreen

sealed class CartScreen(
    @StringRes val name: Int,
    val route: String,
) {

    object OrderConfirmation : CartScreen(
        route = "order_confirmation",
        name = R.string.order_confirmation,
    )
}

fun NavGraphBuilder.cartGraph(navController: NavHostController) {
    composable(CartScreen.OrderConfirmation.route) {
        ConfirmationScreen(navController, hiltViewModel())
    }
    composable(SettingsScreen.OrderDetails.route, SettingsScreen.OrderDetails.arguments) {
        OrderDetailsScreen(it.getOrderId(), hiltViewModel())
    }
}

const val cartGraphRoute = "cart"
