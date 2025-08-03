package kmp.fbk.kmpartgallery.reusable_ui_compomenents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun BasicErrorView(
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(smallPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = message, textAlign = TextAlign.Center)
        OutlinedButton(
            onClick = onRetry,
            content = {
                Text(text = stringResource(Res.string.retry))
            }
        )
    }
}