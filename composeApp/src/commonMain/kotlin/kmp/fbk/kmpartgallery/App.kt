package kmp.fbk.kmpartgallery

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.github.aakira.napier.Napier
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

   val navController = rememberNavController()

   Scaffold(
      snackbarHost = {
         AppSnackBarBannerBarHost(AppSnackBarBannerHostState)
      },
      topBar = {},
      bottomBar = {},
      content = {
         NavigationHost(navController)

         Napier.i(tag = "NavigationHost", message = "NavigationHost: ${navController.getCurrentDestination().screenLabel}")
      }
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