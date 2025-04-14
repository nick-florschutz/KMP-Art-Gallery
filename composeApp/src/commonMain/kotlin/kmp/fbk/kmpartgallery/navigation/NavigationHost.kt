package kmp.fbk.kmpartgallery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kmp.fbk.kmpartgallery.features.listscreen.ListScreen

@Composable
fun NavigationHost(navController: NavHostController) {
//    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Home,
        modifier = Modifier,
    ) {
        composable<NavigationDestination.Home> {
            val args = it.toRoute<NavigationDestination.Home>()
            ListScreen(navController = navController)
        }
    }
}