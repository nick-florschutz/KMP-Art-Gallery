package kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.TextUnit

@Stable
interface ISnackBarBannerVisuals {
    val messageFontSize: TextUnit
    val withDismissAction: Boolean
    val duration: SnackbarDuration
}