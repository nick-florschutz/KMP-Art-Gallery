package reusable_ui_components.snackbar

import androidx.compose.runtime.Stable

@Stable
interface IFbkSnackBarData {
    val visuals: IFbkSnackBarVisuals

    /**
     * Function to be called when Snackbar action has been performed to notify the listeners.
     */
    fun performAction()

    /**
     * Function to be called when Snackbar is dismissed either by timeout or by the user.
     */
    fun dismiss()
}