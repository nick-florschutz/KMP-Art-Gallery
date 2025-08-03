package kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume

/**
 * Custom SnackBar Implementation so that I can use StringResources and customize
 * the look of the SnackBar. Without this I am not able to control the text color of the
 * message inside the snack bar nor am I able to control what types of data I can pass in.
 * With this implementation that is possible.
 */
@Stable
object AppSnackBarBannerHostState {

    /**
     * Only one [Snackbar] can be shown at a time. Since a suspending Mutex is a fair queue, this
     * manages our message queue and we don't have to maintain one.
     */
    private val mutex = Mutex()

    /**
     * The current [SnackbarData] being shown by the [SnackbarHost], or `null` if none.
     */
    var currentSnackbarData by mutableStateOf<ISnackBarBannerData?>(null)
        private set


    suspend fun showSnackBar(
        bannerInformation: BannerInformation,
        messageFontSize: TextUnit = TextUnit.Unspecified,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short,
    ): SnackbarResult =
        showSnackBar(SnackBarBannerVisualsImpl3(bannerInformation, messageFontSize, withDismissAction, duration))

    /**
     * Shows or queues to be shown a [Snackbar] at the bottom of the [Scaffold] to which this state
     * is attached and suspends until the snackbar has disappeared.
     *
     * [SnackbarHostState] guarantees to show at most one snackbar at a time. If this function is
     * called while another snackbar is already visible, it will be suspended until this snackbar is
     * shown and subsequently addressed. If the caller is cancelled, the snackbar will be removed
     * from display and/or the queue to be displayed.
     *
     * All of this allows for granular control over the snackbar queue from within:
     *
     * @sample androidx.compose.material3.samples.ScaffoldWithCustomSnackbar
     *
     * @param visuals [SnackbarVisuals] that are used to create a Snackbar
     *
     * @return [SnackbarResult.ActionPerformed] if option action has been clicked or
     * [SnackbarResult.Dismissed] if snackbar has been dismissed via timeout or by the user
     */
    private suspend fun showSnackBar(visuals: ISnackBarBannerVisuals): SnackbarResult = mutex.withLock {
        try {
            return suspendCancellableCoroutine { continuation ->
                currentSnackbarData = SnackBarBannerDataImpl(visuals, continuation)
            }
        } finally {
            currentSnackbarData = null
        }
    }

    data class SnackBarBannerVisualsImpl3(
        val bannerInformation: BannerInformation,
        override val messageFontSize: TextUnit = TextUnit.Unspecified,
        override val withDismissAction: Boolean,
        override val duration: SnackbarDuration,
    ) : ISnackBarBannerVisuals

    private data class SnackBarBannerDataImpl(
        override val visuals: ISnackBarBannerVisuals,
        private val continuation: CancellableContinuation<SnackbarResult>
    ) : ISnackBarBannerData {

        override fun performAction() {
            if (continuation.isActive) continuation.resume(SnackbarResult.ActionPerformed)
        }

        override fun dismiss() {
            if (continuation.isActive) continuation.resume(SnackbarResult.Dismissed)
        }
    }
}