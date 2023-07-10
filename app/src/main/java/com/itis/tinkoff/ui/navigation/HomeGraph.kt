package com.itis.tinkoff.ui.navigation

import androidx.annotation.StringRes
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.itis.tinkoff.R
import com.itis.tinkoff.ui.navigation.HomeScreen.ProductDetails.getProductId
import com.itis.tinkoff.ui.screens.home.productdetails.DetailsScreen

sealed class HomeScreen(
    @StringRes val name: Int,
    val route: String,
) {

    object ProductDetails : HomeScreen(
        route = "product_details/{productId}",
        name = R.string.product_details,
    ) {

        fun createRoute(orderId: Int) = "product_details/$orderId"

        val arguments = listOf(navArgument("productId") { type = NavType.IntType })

        fun NavBackStackEntry.getProductId() = arguments?.getInt("productId")
    }
}

fun NavGraphBuilder.homeGraph() {
    composable(HomeScreen.ProductDetails.route, HomeScreen.ProductDetails.arguments) {
        DetailsScreen(it.getProductId(), hiltViewModel())
    }
}

const val homeGraphRoute = "home"
