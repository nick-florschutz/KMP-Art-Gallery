package kmp.fbk.kmpartgallery.reusable_ui_compomenents.artpieces

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.navigation.NavigationDestination
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar.AppSnackBarBannerHostState
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.snackbar.BannerInformation
import kmp.fbk.kmpartgallery.smallMediumPadding
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.no_image_available
import kmpartgallery.composeapp.generated.resources.no_results_found
import kmpartgallery.composeapp.generated.resources.unknown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun ArtPieceListItem(
    artPiece: ArtPiece,
    navController: NavController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    platformContext: PlatformContext = LocalPlatformContext.current,
    itemHeight: Dp = remember {
        mutableStateOf(
            if ((artPiece.objectID ?: 0) % 2 == 0) { 250.dp } else { 300.dp }
        )
    }.value
) {
    Column(
        modifier = Modifier
            .height(itemHeight)
            .width(150.dp)
            .clip(
                RoundedCornerShape(smallMediumPadding)
            )
            .clickable {
                artPiece.localId?.let { artPieceId ->
                    navController.navigate(
                        NavigationDestination.DetailView(artPieceLocalId = artPieceId)
                    )
                } ?: run {
                    coroutineScope.launch {
                        AppSnackBarBannerHostState.showSnackBar(
                            BannerInformation.Information(
                                message = artPiece.title ?: "--"
                            )
                        )
                    }
                }
            }
    ) {
        val image = artPiece.primaryImage ?: artPiece.primaryImageSmall ?: artPiece.additionalImages?.firstOrNull()
        if (image.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                Text(
                    text = stringResource(Res.string.no_image_available),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = smallPadding)
                )
            }
        } else {
            AsyncImage(
                model = ImageRequest.Builder(platformContext)
                    .data(image)
                    .build(),
                contentDescription = null,
                placeholder = rememberVectorPainter(Icons.Default.FileDownload),
                fallback = rememberVectorPainter(Icons.Default.Error),
                error = rememberVectorPainter(Icons.Default.Error),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .weight(0.3f)
                .padding(extraSmallPadding)
        ) {
            Text(
                text = artPiece.title ?: "--",
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = artPiece.artistDisplayName ?: stringResource(Res.string.unknown),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}