package kmp.fbk.kmpartgallery.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalAccessibilityManager
import kotlinx.coroutines.delay
import reusable_ui_components.snackbar.FadeInFadeOutWithScale
import reusable_ui_components.snackbar.IFbkSnackBarData
import reusable_ui_components.snackbar.toMillis

@Composable
fun FbkSnackBarHost(
    snackBarHostState: FbkSnackBarHostState,
    modifier: Modifier = Modifier,
) {
    FbkSnackBarHostInternal(
        hostState = snackBarHostState,
        snackbar = { data ->
            FbkSnackBar2(
                snackBarData = data,
                onDismiss = {
                    FbkSnackBarHostState.currentSnackbarData?.dismiss()
                },
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun FbkSnackBarHostInternal(
    hostState: FbkSnackBarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (IFbkSnackBarData) -> Unit,
) {
    val currentSnackbarData = FbkSnackBarHostState.currentSnackbarData
    val accessibilityManager = LocalAccessibilityManager.current
    LaunchedEffect(currentSnackbarData) {
        if (currentSnackbarData != null) {
            val duration = currentSnackbarData.visuals.duration.toMillis(
                FbkSnackBarHostState.currentSnackbarData?.visuals?.withDismissAction == true,
                accessibilityManager
            )
            delay(duration)
            currentSnackbarData.dismiss()
        }
    }
    FadeInFadeOutWithScale(
        current = FbkSnackBarHostState.currentSnackbarData,
        modifier = modifier,
        content = snackbar
    )
}