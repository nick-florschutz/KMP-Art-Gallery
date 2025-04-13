package kmp.fbk.kmpartgallery.features.listscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.LocalPlatformContext
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.networking.download.ArtPieceDownloadMachine
import kmp.fbk.kmpartgallery.networking.download.DepartmentsDownloadMachine
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.sort_down
import kmpartgallery.composeapp.generated.resources.the_metropolitan_museum_of_art_logo
import kotlinx.serialization.json.JsonNull.content
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform

@Composable
fun ListScreen() {

    val listScreenRepository = KoinPlatform.getKoin().get<ListScreenRepository>()
    val departmentsDownloadMachine = KoinPlatform.getKoin().get<DepartmentsDownloadMachine>()
    val artPieceDownloadMachine = KoinPlatform.getKoin().get<ArtPieceDownloadMachine>()
    val viewModel = viewModel {
        ListScreenViewModel(
            listScreenRepository = listScreenRepository,
            departmentsDownloadMachine = departmentsDownloadMachine,
            artPieceDownloadMachine = artPieceDownloadMachine,
        )
    }

    val coroutineScope = rememberCoroutineScope()
    val platformContext = LocalPlatformContext.current

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val departments by viewModel.departmentsList.collectAsStateWithLifecycle()
    val artPieces by viewModel.artPieceResponseList.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .padding(mediumPadding)
                ) {

                    Icon(
                        painter = painterResource(Res.drawable.the_metropolitan_museum_of_art_logo),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )

                    // TODO: Add Search bar here

                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.wrapContentHeight()
            ) {
                NavigationBarItem(
                    selected = true, // TODO: Replace with actual selected state
                    onClick = {
                        // TODO
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    },
                    label = {
                        Text(text = "Home")
                    },
                )

                NavigationBarItem(
                    selected = false, // TODO: Replace with actual selected state
                    onClick = {
                        // TODO
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Explore, contentDescription = null)
                    },
                    label = {
                        Text(text = "Explore")
                    },
                )

                NavigationBarItem(
                    selected = false, // TODO: Replace with actual selected state
                    onClick = {
                        // TODO
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.CollectionsBookmark, contentDescription = null)
                    },
                    label = {
                        Text(text = "Collections")
                    },
                )

                NavigationBarItem(
                    selected = false, // TODO: Replace with actual selected state
                    onClick = {
                        // TODO
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    },
                    label = {
                        Text(text = "Profile")
                    },
                )

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.sort_down),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
            .systemBarsPadding(),
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState is ViewModelState.Loading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            ArtPieceStaggeredGrid(artPieces = artPieces)
        }
    }
}