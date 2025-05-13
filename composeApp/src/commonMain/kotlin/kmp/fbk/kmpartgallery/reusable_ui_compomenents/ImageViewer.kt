package kmp.fbk.kmpartgallery.reusable_ui_compomenents

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest

@Composable
fun ImageViewer(
    modifier: Modifier = Modifier,
    image: String,
    imageContentDescription: String = "",
) {
    val platformContext = LocalPlatformContext.current

    // Mutable state variables to hold scale and offset values
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val minScale = 1f
    val maxScale = 5f

    // Remember the initial offset
    var initialOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    // Coefficient for slowing down movement
    val slowMovement = 0.5f

    // Box composable containing the image
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    // Update scale with the zoom
                    val newScale = scale * zoom
                    scale = newScale.coerceIn(minScale, maxScale)

                    // Calculate new offsets based on zoom and pan
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val offsetXChange = (centerX - offsetX) * (newScale / scale - 1)
                    val offsetYChange = (centerY - offsetY) * (newScale / scale - 1)

                    // Calculate min and max offsets
                    val maxOffsetX = (size.width / 2) * (scale - 1)
                    val minOffsetX = -maxOffsetX
                    val maxOffsetY = (size.height / 2) * (scale - 1)
                    val minOffsetY = -maxOffsetY

                    // Update offsets while ensuring they stay within bounds
                    if (scale * zoom <= maxScale) {
                        offsetX = (offsetX + pan.x * scale * slowMovement + offsetXChange)
                            .coerceIn(minOffsetX, maxOffsetX)
                        offsetY = (offsetY + pan.y * scale * slowMovement + offsetYChange)
                            .coerceIn(minOffsetY, maxOffsetY)
                    }

                    // Store initial offset on pan
                    if (pan != Offset(0f, 0f) && initialOffset == Offset(0f, 0f)) {
                        initialOffset = Offset(offsetX, offsetY)
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        // Reset scale and offset on double tap
                        if (scale != 1f) {
                            scale = 1f
                            offsetX = initialOffset.x
                            offsetY = initialOffset.y
                        } else {
                            scale = 2f
                        }
                    }
                )
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offsetX
                translationY = offsetY
            }
    ) {
        // Image to be displayed with pinch-to-zoom functionality
        AsyncImage(
            model = ImageRequest.Builder(platformContext)
                .data(image)
                .build(),
            contentDescription = null,
            placeholder = rememberVectorPainter(Icons.Default.FileDownload),
            fallback = rememberVectorPainter(Icons.Default.Error),
            error = rememberVectorPainter(Icons.Default.Error),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
    }
}