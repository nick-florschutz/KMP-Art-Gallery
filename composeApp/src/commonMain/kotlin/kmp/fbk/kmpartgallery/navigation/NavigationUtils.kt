package kmp.fbk.kmpartgallery.navigation

import androidx.navigation.NavHostController

fun NavHostController.getCurrentDestination(): NavigationDestination? {
    return NavigationDestination.fromString(currentBackStackEntry?.destination?.route)
}