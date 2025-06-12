package kmp.fbk.kmpartgallery.features.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kmp.fbk.kmpartgallery.ViewModelState
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.error_loading_artists
import kmpartgallery.composeapp.generated.resources.no_artists_found
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
        is ViewModelState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator()
            }
        }

        is ViewModelState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(text = stringResource(Res.string.error_loading_artists))
            }
        }

        is ViewModelState.Success -> {
            ArtistsList((viewModelState as ViewModelState.Success).data as List<String>)
        }
    }
}

@Composable
private fun ArtistsList(artists: List<String>) {
    if (artists.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = stringResource(Res.string.no_artists_found))
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(smallPadding),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = smallPadding)
        ) {
            items(artists) { artist ->
                Text(text = artist)

                Spacer(modifier = Modifier.height(smallPadding))

                if (artist != artists.last()) {
                    HorizontalDivider(Modifier.fillMaxWidth())
                }
            }
        }
    }
}