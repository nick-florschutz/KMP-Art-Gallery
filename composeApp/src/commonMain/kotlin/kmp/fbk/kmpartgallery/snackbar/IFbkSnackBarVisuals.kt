package reusable_ui_components.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.TextUnit
import org.jetbrains.compose.resources.StringResource

@Stable
interface IFbkSnackBarVisuals {
//    val message: StringResource
    val messageFontSize: TextUnit
    val withDismissAction: Boolean
    val duration: SnackbarDuration
}