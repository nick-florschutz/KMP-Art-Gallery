package kmp.fbk.kmpartgallery

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import kmp.fbk.kmpartgallery.di.createKoinConfiguration
import kmp.fbk.kmpartgallery.navigation.NavigationHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication

@Composable
@Preview
fun App() {
    KoinMultiplatformApplication(
        config = createKoinConfiguration(),
    ) {
        MaterialTheme {
           NavigationHost()
        }
    }
}