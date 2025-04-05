package kmp.fbk.kmpartgallery

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kmp.fbk.kmpartgallery.navigation.NavigationHost
import kmp.fbk.kmpartgallery.snackbar.FbkSnackBarHost
import kmp.fbk.kmpartgallery.snackbar.FbkSnackBarHostState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

   Scaffold(
      topBar = {},
      snackbarHost = {
         FbkSnackBarHost(FbkSnackBarHostState)
      },
      content = { paddingValues ->
         NavigationHost(modifier = Modifier.padding(paddingValues))
      }
   )

}