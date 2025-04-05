package kmp.fbk.kmpartgallery.snackbar

import androidx.compose.runtime.Stable

@Stable
interface ISnackBarBannerData {
    val visuals: ISnackBarBannerVisuals

    /**
     * Function to be called when Snackbar action has been performed to notify the listeners.
     */
    fun performAction()

    /**
     * Function to be called when Snackbar is dismissed either by timeout or by the user.
     */
    fun dismiss()
}