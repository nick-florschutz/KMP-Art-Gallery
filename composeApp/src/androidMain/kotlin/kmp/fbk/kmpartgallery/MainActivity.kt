package kmp.fbk.kmpartgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.app_theme.AppTheme
import kmp.fbk.kmpartgallery.di.appModules
import kmp.fbk.kmpartgallery.di.getRoomDatabaseModule
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val androidModules = listOf(
            getRoomDatabaseModule(this)
        )

        startKoin {
            modules(androidModules + appModules())
        }

        // Initialize Napier Logger
        Napier.base(DebugAntilog())


        setContent {
            AppTheme {
                App()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clear Napier Logs
        Napier.takeLogarithm()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}