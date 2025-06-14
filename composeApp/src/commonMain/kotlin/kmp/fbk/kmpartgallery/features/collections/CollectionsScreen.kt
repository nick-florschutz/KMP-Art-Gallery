package kmp.fbk.kmpartgallery.features.collections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import kmp.fbk.kmpartgallery.ViewModelState
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.navigation.NavigationDestination
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.BasicErrorView
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.BasicLoadingView
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.artpieces.ArtPieceListItem
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.see_all
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform

@Composable
fun CollectionsScreen(
    navController: NavController,
) {
    val viewModel = viewModel {
        val repository = KoinPlatform.getKoin().get<CollectionsRepository>()
        CollectionsScreenViewModel(
            repository = repository,
        ).also { vm ->
            vm.loadArtPiecesAndDepartments()
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

            val artPiecesAndDepartmentsMap = (uiState as ViewModelState.Success).data as Map<String, List<ArtPiece>>

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(mediumPadding),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(artPiecesAndDepartmentsMap.keys.toList()) { department ->
                    HorizontalArtPieceListWithHeader(
                        department = department,
                        artPieces = artPiecesAndDepartmentsMap[department] ?: emptyList(),
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Composable
private fun HorizontalArtPieceListWithHeader(
    department: String,
    artPieces: List<ArtPiece>,
    navController: NavController,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = mediumPadding),
        ) {
            Text(text = department, fontWeight = FontWeight.Medium)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        navController.navigate(
                            NavigationDestination.CollectionByDepartment(department)
                        )
                    }
                    .padding(vertical = extraSmallPadding, horizontal = smallPadding)
            ) {
                Text(text = stringResource(Res.string.see_all), color = Color.Blue)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    tint = Color.Blue,
                    contentDescription = null,
                )
            }
        }

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(smallPadding),
            contentPadding = PaddingValues(
                horizontal = mediumPadding,
                vertical = smallPadding,
            ),
        ) {
            items(artPieces) { artPiece ->
                ArtPieceListItem(
                    artPiece = artPiece,
                    navController = navController,
                    itemHeight = 250.dp,
                    modifier = Modifier.animateItem(),
                )
            }
        }
    }
}