package kmp.fbk.kmpartgallery.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kmp.fbk.kmpartgallery.features.details.DetailViewScreen
import kmp.fbk.kmpartgallery.features.listscreen.ListScreen

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Home,
        modifier = modifier,
    ) {
        composable<NavigationDestination.Home> {
            val args = it.toRoute<NavigationDestination.Home>()
            ListScreen(navController = navController)
        }

        composable<NavigationDestination.Explore> {
            val args = it.toRoute<NavigationDestination.Explore>()
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = args.screenLabel,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        composable<NavigationDestination.Collections> {
            val args = it.toRoute<NavigationDestination.Collections>()
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = args.screenLabel,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        composable<NavigationDestination.Artists> {
            val args = it.toRoute<NavigationDestination.Artists>()
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = args.screenLabel,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        composable<NavigationDestination.DetailView> {
            val args = it.toRoute<NavigationDestination.DetailView>()
            val artPieceLocalId = args.artPieceLocalId ?: throw IllegalStateException(
                "artPieceLocalId is required. artPieceLocalId: ${args.artPieceLocalId}"
            )
            DetailViewScreen(
                artPieceLocalId = artPieceLocalId,
                navController = navController,
            )
        }

    }
}