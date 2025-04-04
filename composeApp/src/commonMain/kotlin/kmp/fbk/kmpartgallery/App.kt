package kmp.fbk.kmpartgallery

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kmp.fbk.kmpartgallery.navigation.NavigationHost
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
//    KoinMultiplatformApplication(
//        config = createKoinConfiguration(),
//    ) {
        MaterialTheme {
           NavigationHost()
        }
//    }
}