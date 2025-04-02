package kmp.fbk.kmpartgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Napier Logger
        Napier.base(DebugAntilog())


        setContent {
            App()
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