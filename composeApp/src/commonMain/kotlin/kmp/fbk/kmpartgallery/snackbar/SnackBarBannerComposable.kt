package kmp.fbk.kmpartgallery.snackbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kmp.fbk.kmpartgallery.snackbar.AppSnackBarBannerHostState
import kmp.fbk.kmpartgallery.snackbar.ISnackBarBannerData
import kmp.fbk.kmpartgallery.snackbar.SnackBarBanner
import kotlin.jvm.JvmName

@Composable
internal fun SnackBarBanner1(
    snackBarData: ISnackBarBannerData,
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    if (snackBarData.visuals is AppSnackBarBannerHostState.SnackBarBannerVisualsImpl3) {
        SnackBarBanner(
            bannerInfo = (snackBarData.visuals as AppSnackBarBannerHostState.SnackBarBannerVisualsImpl3).bannerInformation,
            hasDismissAction = snackBarData.visuals.withDismissAction,
            onDismiss = onDismiss,
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(75.dp)
                .padding(8.dp)
        )

    } else {
        throw IllegalArgumentException("Unknown SnackBar visuals")
    }
}