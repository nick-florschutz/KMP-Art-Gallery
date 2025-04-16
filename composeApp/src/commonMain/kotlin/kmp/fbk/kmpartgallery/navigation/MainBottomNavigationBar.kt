package kmp.fbk.kmpartgallery.navigation

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainBottomNavigationBar(
    currentDestination: NavigationDestination?,
    navController: NavController,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.wrapContentHeight()
    ) {
        NavigationBarItem(
            selected = currentDestination == NavigationDestination.Home,
            onClick = {
                navController.navigate(NavigationDestination.Home)
            },
            icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            },
            label = {
                Text(text = NavigationDestination.Home.screenLabel)
            },
        )

        NavigationBarItem(
            selected = currentDestination == NavigationDestination.Explore,
            onClick = {
                navController.navigate(NavigationDestination.Explore)
            },
            icon = {
                Icon(imageVector = Icons.Default.Explore, contentDescription = null)
            },
            label = {
                Text(text = NavigationDestination.Explore.screenLabel)
            },
        )

        NavigationBarItem(
            selected = currentDestination == NavigationDestination.Collections,
            onClick = {
                navController.navigate(NavigationDestination.Collections)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.CollectionsBookmark,
                    contentDescription = null
                )
            },
            label = {
                Text(text = NavigationDestination.Collections.screenLabel)
            },
        )

        NavigationBarItem(
            selected = currentDestination == NavigationDestination.Artists,
            onClick = {
                navController.navigate(NavigationDestination.Artists)
            },
            icon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            label = {
                Text(text = NavigationDestination.Artists.screenLabel)
            },
        )

    }
}