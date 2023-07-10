package com.itis.tinkoff.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = authGraphRoute) {
        authGraph(navController)
        mainGraph(navController)
    }
}


fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.graph.findStartDestination().id) {
        inclusive = true
    }
}

fun NavOptionsBuilder.popUpToFirst(navController: NavController) {
    popUpTo(navController.graph.findStartDestination().id) {
        inclusive = false
    }
}
