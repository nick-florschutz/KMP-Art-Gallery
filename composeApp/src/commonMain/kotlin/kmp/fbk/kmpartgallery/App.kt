package kmp.fbk.kmpartgallery

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.navigation.MainBottomNavigationBar
import kmp.fbk.kmpartgallery.navigation.NavigationDestination
import kmp.fbk.kmpartgallery.navigation.NavigationHost
import kmp.fbk.kmpartgallery.navigation.getCurrentDestination
import kmp.fbk.kmpartgallery.snackbar.AppSnackBarBannerBarHost
import kmp.fbk.kmpartgallery.snackbar.AppSnackBarBannerHostState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

   setSingletonImageLoaderFactory { context ->
      getAsyncImageLoader(context)
   }

   val layoutDirection = LocalLayoutDirection.current

   val navController = rememberNavController().apply {
      addOnDestinationChangedListener { controller, _, _ ->
         Napier.i(tag = "NavigationHost", message = "Current Destination: ${this.getCurrentDestination()?.screenLabel}")
      }
   }

   var currentDestination by remember {
      mutableStateOf(navController.getCurrentDestination())
   }
   LaunchedEffect(Unit) {
      navController.addOnDestinationChangedListener { controller, _, _ ->
         currentDestination = navController.getCurrentDestination()
      }
   }

   Scaffold(
      snackbarHost = {
         AppSnackBarBannerBarHost(AppSnackBarBannerHostState)
      },
      topBar = {
         currentDestination?.getTopBarContent()
      },
      bottomBar = {
         if (currentDestination?.showBottonNavigator == true) {
            MainBottomNavigationBar(currentDestination, navController)
         }
      },
      content = { paddingValues ->
         NavigationHost(
            navController = navController,
            modifier = Modifier
               .padding(
                  start = paddingValues.calculateStartPadding(layoutDirection),
                  end = paddingValues.calculateEndPadding(layoutDirection),
                  bottom = paddingValues.calculateBottomPadding(),
               )
         )
      },
      modifier = Modifier.then(
         if (currentDestination?.expandToEdgeOfScreen == false) {
            Modifier.systemBarsPadding()
         } else {
            Modifier
         }
      )
   )

}

fun getAsyncImageLoader(context: PlatformContext) =
   ImageLoader.Builder(context)
      .crossfade(true)
      .components {
         add(
            KtorNetworkFetcherFactory()
         )
      }
      .logger(DebugLogger())
      .build()