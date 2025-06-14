package kmp.fbk.kmpartgallery.features.collections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kmp.fbk.kmpartgallery.ViewModelState
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.BasicErrorView
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.BasicLoadingView
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.artpieces.ArtPieceListItem
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.the_metropolitan_museum_of_art_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform

@Composable
fun CollectionsByDepartmentScreen(
    department: String,
    navController: NavController,
) {

    val viewModel = viewModel {
        val repository = KoinPlatform.getKoin().get<CollectionsRepository>()
        CollectionsScreenViewModel(
            repository = repository,
        ).also { vm ->
            vm.getArtPiecesByDepartment(department)
        }
    }

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    when (uiState) {
        is ViewModelState.Error -> {
            BasicErrorView(
                message = "Error Loading Collections",
                onRetry = { /* TODO */ },
            )
        }

        ViewModelState.Loading -> BasicLoadingView()

        is ViewModelState.Success -> {
            val artPieces = (uiState as ViewModelState.Success).data as List<ArtPiece>

            Scaffold(
                topBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(color = MaterialTheme.colorScheme.surface)
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                            )
                        }

                        Text(
                            text = department,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                modifier = Modifier.systemBarsPadding()
            ) { paddingValues ->
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(152.dp),
                    verticalItemSpacing = smallPadding,
                    horizontalArrangement = Arrangement.spacedBy(smallPadding),
                    contentPadding = PaddingValues(horizontal = mediumPadding),
                    content = {
                        items(artPieces) { artPiece ->
                            ArtPieceListItem(
                                artPiece = artPiece,
                                navController = navController,
                                modifier = Modifier.animateItem(),
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }

}