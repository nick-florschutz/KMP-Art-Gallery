package kmp.fbk.kmpartgallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.networking.MetArtMuseumApiRequests
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun TestScreen(screenName: String) {
    val coroutineScope = rememberCoroutineScope()

    var showContent by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "You are on the $screenName screen")

        Button(
            onClick = {
                showContent = !showContent

                coroutineScope.launch(Dispatchers.Default) {
                    val result = MetArtMuseumApiRequests.getAllObjectIds()
                    Napier.i(tag = "TestScreen", message = "result: $result")
                }

            }
        ) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
            }
        }
    }
}