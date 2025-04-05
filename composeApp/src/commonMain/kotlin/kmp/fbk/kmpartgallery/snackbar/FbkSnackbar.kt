package kmp.fbk.kmpartgallery.snackbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import reusable_ui_components.snackbar.IFbkSnackBarData

@Composable
internal fun FbkSnackBar2(
    snackBarData: IFbkSnackBarData,
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    if (snackBarData.visuals is FbkSnackBarHostState.FbkSnackBarVisualsImpl3) {
        FbkBanner(
            bannerInfo = (snackBarData.visuals as FbkSnackBarHostState.FbkSnackBarVisualsImpl3).bannerInformation,
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