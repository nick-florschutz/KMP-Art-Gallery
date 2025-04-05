package kmp.fbk.kmpartgallery

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import kmp.fbk.kmpartgallery.navigation.NavigationHost
import kmp.fbk.kmpartgallery.snackbar.AppSnackBarBannerBarHost
import kmp.fbk.kmpartgallery.snackbar.AppSnackBarBannerHostState
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {

   setSingletonImageLoaderFactory { context ->
      getAsyncImageLoader(context)
   }

   Scaffold(
      topBar = {},
      snackbarHost = {
         AppSnackBarBannerBarHost(AppSnackBarBannerHostState)
      },
      content = { paddingValues ->
         NavigationHost(modifier = Modifier.padding(paddingValues))
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