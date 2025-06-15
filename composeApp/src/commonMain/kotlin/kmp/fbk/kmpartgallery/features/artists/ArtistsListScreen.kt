package kmp.fbk.kmpartgallery.features.artists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.ViewModelState
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.getScreenHeight
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.BasicErrorView
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.BasicLoadingView
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.error_loading_artists
import kmpartgallery.composeapp.generated.resources.no_artists_found
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform

@Composable
fun ArtistsScreen(
    navController: NavController,
) {
    val viewModel = viewModel {
        val artistsScreenRepository = KoinPlatform.getKoin().get<ArtistsScreenRepository>()
        ArtistsScreenViewModel(
            repository = artistsScreenRepository,
        )
    }
    val viewModelState by viewModel.state.collectAsStateWithLifecycle()

    when (viewModelState) {
        is ViewModelState.Loading -> BasicLoadingView()

        is ViewModelState.Error -> {
            BasicErrorView(
                message = stringResource(Res.string.error_loading_artists),
                onRetry = { /* TODO */ }
            )
        }

        is ViewModelState.Success -> {
            ArtistsList((viewModelState as ViewModelState.Success).data as Map<String, List<String>>)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ArtistsList(
    artists: Map<String, List<String>>,
    lazyListState: LazyListState = rememberLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    if (artists.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = stringResource(Res.string.no_artists_found))
        }
    } else {

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                artists.forEach { (sectionHeader, artists) ->
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Text(
                                text = sectionHeader,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                    .fillMaxWidth()
                                    .padding(smallPadding)
                            )
                        }
                    }
                    items(
                        items = artists,
                        key = { it },
                    ) { artist ->
                        ArtistListItem(
                            artist = artist,
                            lastArtist = artists.last() == artist,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem()
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(horizontal = smallPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                artists.keys.forEach { section ->
                    Text(
                        text = section,
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    val indicesBeforeTappedSection = artists.keys.indexOf(section)
                                    var count = 0
                                    // Calculate number of items before tapped section
                                    artists.keys.forEach {
                                        if (artists.keys.indexOf(it) < indicesBeforeTappedSection) {
                                            // Size & +1 to account for header
                                            count += (artists[it]?.size?.plus(1) ?: 0)
                                        } else {
                                            return@forEach
                                        }
                                    }

                                    lazyListState.scrollToItem(index = count)
                                }
                            }
                            .padding(extraSmallPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ArtistListItem(
    artist: String,
    lastArtist: Boolean,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(smallPadding))

        Text(
            text = artist,
            modifier = Modifier.padding(horizontal = smallPadding)
        )

        Spacer(modifier = Modifier.height(smallPadding))

        if (!lastArtist) {
            HorizontalDivider(Modifier.fillMaxWidth())
        }
    }
}