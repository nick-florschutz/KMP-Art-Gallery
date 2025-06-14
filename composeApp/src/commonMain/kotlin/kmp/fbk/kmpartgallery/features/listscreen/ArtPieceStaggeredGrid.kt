package kmp.fbk.kmpartgallery.features.listscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.LocalPlatformContext
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.getScreenHeight
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.artpieces.ArtPieceListItem
import kmp.fbk.kmpartgallery.smallPadding

@Composable
fun ArtPieceStaggeredGrid(
    artPieces: List<ArtPiece>,
    lazyStaggeredGridState: LazyStaggeredGridState,
    mainListState: LazyListState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val screenHeight = getScreenHeight()

    val coroutineScope = rememberCoroutineScope()
    val platformContext = LocalPlatformContext.current

    val isScrollEnabled by remember {
        derivedStateOf {
            mainListState.firstVisibleItemIndex >= 1
                    || mainListState.firstVisibleItemScrollOffset > screenHeight.value
        }
    }

    LazyVerticalStaggeredGrid(
        state = lazyStaggeredGridState,
        columns = StaggeredGridCells.Adaptive(152.dp),
        verticalItemSpacing = smallPadding,
        userScrollEnabled = isScrollEnabled,
        horizontalArrangement = Arrangement.spacedBy(smallPadding),
        contentPadding = PaddingValues(horizontal = mediumPadding),
        content = {
            items(artPieces) { artPiece ->
                ArtPieceListItem(
                    artPiece = artPiece,
                    navController = navController,
                    coroutineScope = coroutineScope,
                    platformContext = platformContext,
                    modifier = Modifier
                        .animateItem()
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight)
    )
}