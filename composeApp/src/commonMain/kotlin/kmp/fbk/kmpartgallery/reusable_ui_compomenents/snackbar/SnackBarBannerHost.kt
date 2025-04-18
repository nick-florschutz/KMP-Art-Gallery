package kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalAccessibilityManager
import kotlinx.coroutines.delay
import reusable_ui_components.snackbar.FadeInFadeOutWithScale
import reusable_ui_components.snackbar.toMillis

@Composable
fun AppSnackBarBannerBarHost(
    snackBarHostState: AppSnackBarBannerHostState,
    modifier: Modifier = Modifier,
) {
    AppSnackBarBannerHostInternal(
        hostState = snackBarHostState,
        snackbar = { data ->
            SnackBarBanner1(
                snackBarData = data,
                onDismiss = {
                    AppSnackBarBannerHostState.currentSnackbarData?.dismiss()
                },
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun AppSnackBarBannerHostInternal(
    hostState: AppSnackBarBannerHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (ISnackBarBannerData) -> Unit,
) {
    val currentSnackbarData = AppSnackBarBannerHostState.currentSnackbarData
    val accessibilityManager = LocalAccessibilityManager.current
    LaunchedEffect(currentSnackbarData) {
        if (currentSnackbarData != null) {
            val duration = currentSnackbarData.visuals.duration.toMillis(
                AppSnackBarBannerHostState.currentSnackbarData?.visuals?.withDismissAction == true,
                accessibilityManager
            )
            delay(duration)
            currentSnackbarData.dismiss()
        }
    }
    FadeInFadeOutWithScale(
        current = AppSnackBarBannerHostState.currentSnackbarData,
        modifier = modifier,
        content = snackbar
    )
}