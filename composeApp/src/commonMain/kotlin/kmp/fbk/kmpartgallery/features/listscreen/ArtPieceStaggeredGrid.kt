package kmp.fbk.kmpartgallery.features.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtPieceResponse
import kmp.fbk.kmpartgallery.smallMediumPadding
import kmp.fbk.kmpartgallery.smallPadding
import kmp.fbk.kmpartgallery.snackbar.AppSnackBarBannerHostState
import kmp.fbk.kmpartgallery.snackbar.BannerInformation
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.random.Random

@Composable
fun ArtPieceStaggeredGrid(
    artPieces: List<ArtPieceResponse>,
) {
    val coroutineScope = rememberCoroutineScope()
    val platformContext = LocalPlatformContext.current

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(152.dp),
        verticalItemSpacing = smallPadding,
        horizontalArrangement = Arrangement.spacedBy(smallPadding),
        contentPadding = PaddingValues(horizontal = mediumPadding),
        content = {
            items(artPieces) { artPiece ->
                val height by remember {
                    mutableStateOf(
                        if ((artPiece.objectID?:0) % 2 == 0) {
                            250.dp
                        } else {
                            300.dp
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .height(height)
                        .width(150.dp)
                        .clip(
                            RoundedCornerShape(smallMediumPadding)
                        )
                        .clickable {
                            coroutineScope.launch {
                                AppSnackBarBannerHostState.showSnackBar(
                                    BannerInformation.Information(
                                        message = artPiece.title ?: "--"
                                    )
                                )
                            }
                        }
                ) {
                    val image = artPiece.primaryImage ?: artPiece.primaryImageSmall ?: artPiece.additionalImages?.firstOrNull()
                    if (image != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(platformContext)
                                .data(image)
                                .build(),
                            contentDescription = null,
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
                            .padding(smallPadding)
                    ) {
                        Text(text = artPiece.title ?: "--", fontSize = 14.sp)
                        Text(text = artPiece.artistDisplayName ?: "Unknown", fontSize = 12.sp)
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    )


}