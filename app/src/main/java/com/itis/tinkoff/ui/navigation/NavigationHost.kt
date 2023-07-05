package com.itis.tinkoff.ui.navigation

/*sealed class Screen(
    val icon: ImageVector,
    @StringRes val name: Int = 0,
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {

    object Main : Screen(
        route = "main",
        name = R.string.films,
        icon = Icons.Filled.Home,
    )

    object Settings : Screen(
        route = "settings",
        name = R.string.settings,
        icon = Icons.Filled.Settings,
    )

    object Stub : Screen(
        route = "stub",
        name = R.string.stub,
        icon = Icons.Filled.Info,
    )

    class Details(
        filmId: Int = 0,
        val processedRoute: String = "details/$filmId"
    ) : Screen(
        route = "details/{filmId}",
        icon = Icons.Filled.Home,
        arguments = listOf(navArgument("filmId") { type = NavType.IntType }),
    )
}



@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Screen = Screen.Main
) {
    NavHost(
        navController,
        startDestination = startDestination.route,
        androidx.compose.ui.Modifier.padding(innerPadding),
    ) {
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.Stub.route) { StubScreen() }
        composable(Screen.Details().route, Screen.Details().arguments) {
            DetailsScreen(it.arguments?.getInt("filmId"))
        }
    }
}*/
