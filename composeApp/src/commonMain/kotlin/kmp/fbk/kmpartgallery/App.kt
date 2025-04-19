package kmp.fbk.kmpartgallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.MainBottomNavigationBar
import kmp.fbk.kmpartgallery.navigation.NavigationHost
import kmp.fbk.kmpartgallery.navigation.getCurrentDestination
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar.AppSnackBarBannerBarHost
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar.AppSnackBarBannerHostState
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform

@Composable
@Preview
fun App() {

   setSingletonImageLoaderFactory { context ->
      getAsyncImageLoader(context)
   }

   LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
      val appInitializer = KoinPlatform.getKoin().get<AppInitializer>()
      appInitializer.runInitJobs()
   }

   val layoutDirection = LocalLayoutDirection.current

   val navController = rememberNavController()

   var currentDestination by remember {
      mutableStateOf(navController.getCurrentDestination())
   }
   LaunchedEffect(Unit) {
      navController.addOnDestinationChangedListener { controller, _, _ ->
         currentDestination = navController.getCurrentDestination()?.also {
            Napier.i(tag = "NavigationHost", message = "Current Destination: ${it.screenLabel}")
         }
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
         AnimatedVisibility(
            visible = currentDestination?.showBottomNavigator == true,
            enter = slideInVertically(initialOffsetY = {it}),
            exit = slideOutVertically(targetOffsetY = {it}),
         ) {
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