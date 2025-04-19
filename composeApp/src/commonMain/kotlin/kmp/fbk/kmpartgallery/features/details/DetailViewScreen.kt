package kmp.fbk.kmpartgallery.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kmp.fbk.kmpartgallery.ViewModelState
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.getScreenHeight
import kmp.fbk.kmpartgallery.largePadding
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.smallPadding
import org.koin.mp.KoinPlatform

private const val DETAIL_CHIP_CHAR_LIMIT = 50

@Composable
fun DetailViewScreen(
    artPieceLocalId: Long,
    navController: NavController,
) {
    val viewModel = viewModel {
        val detailViewScreenRepository = KoinPlatform.getKoin().get<DetailViewScreenRepository>()
        DetailViewScreenViewModel(
            artPieceLocalId = artPieceLocalId,
            detailViewScreenRepository = detailViewScreenRepository,
        )
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val artPiece by viewModel.artPiece.collectAsStateWithLifecycle()

    val platformContext = LocalPlatformContext.current

    when (uiState) {
        is ViewModelState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Error Loading Art Piece")
                // TODO: Handle Error
            }
        }
        ViewModelState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is ViewModelState.Success -> {
            val artPieceValue = artPiece ?: return
            DetailViewScreenContent(
                artPiece = artPieceValue,
                viewModel = viewModel,
                platformContext = platformContext,
                navController = navController,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailViewScreenContent(
    artPiece: ArtPiece,
    viewModel: DetailViewScreenViewModel,
    platformContext: PlatformContext,
    navController: NavController,
) {
    val screenHeight = getScreenHeight()
    val imageHeight = remember {
        screenHeight / 1.7f
    }

    var isDetailsSectionExpanded by rememberSaveable { mutableStateOf(false) }
    var isHistoricalSectionExpanded by rememberSaveable { mutableStateOf(false) }
    var isArtistsSectionExpanded by rememberSaveable { mutableStateOf(false) }
    var isTechnicalInformationSectionExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier.height(imageHeight)
                .background(Color.Black),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(platformContext)
                    .data(viewModel.determineWhichImageToUse())
                    .build(),
                contentDescription = null,
                placeholder = rememberVectorPainter(Icons.Default.FileDownload),
                fallback = rememberVectorPainter(Icons.Default.Error),
                error = rememberVectorPainter(Icons.Default.Error),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .systemBarsPadding()
                    .padding(start = smallPadding)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(smallPadding),
        ) {
            Text(
                text = artPiece.title ?: "No Art Piece Found",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = mediumPadding, top = mediumPadding, end = mediumPadding)
            )

           Row(
               horizontalArrangement = Arrangement.spacedBy(smallPadding),
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier.padding(start = mediumPadding, top = smallPadding)
           ) {
               Text(
                   text = artPiece.artistDisplayName?.takeIf { it.isNotBlank() }  ?: "Unknown",
                   fontSize = 16.sp,
                   color = if (artPiece.artistDisplayName.isNullOrBlank()) Color.Gray else Color.Blue,
               )

               Icon(
                   imageVector = Icons.Default.Circle,
                   tint = Color.Gray,
                   contentDescription = null,
                   modifier = Modifier.size(6.dp)
               )

               Text(
                   text = artPiece.objectDate?.takeIf { it.isNotBlank() } ?: "Unknown",
                   fontSize = 16.sp,
                   modifier = Modifier
               )
           }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(smallPadding),
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(mediumPadding)
            ) {
                if (!artPiece.objectName.isNullOrBlank()) {
                    DetailViewScreenInfoChip(label = artPiece.objectName)
                }

                if (!artPiece.medium.isNullOrBlank() && artPiece.medium.length < DETAIL_CHIP_CHAR_LIMIT) {
                    DetailViewScreenInfoChip(label = artPiece.medium)
                }

                if (!artPiece.culture.isNullOrBlank()) {
                    DetailViewScreenInfoChip(label = artPiece.culture)
                }

                if (!artPiece.period.isNullOrBlank()) {
                    DetailViewScreenInfoChip(label = artPiece.period)
                }

                if (!artPiece.dimensions.isNullOrBlank() && artPiece.dimensions.length < DETAIL_CHIP_CHAR_LIMIT) {
                    DetailViewScreenInfoChip(label = artPiece.dimensions)
                }
            }

            if (!artPiece.creditLine.isNullOrBlank()) {
                Text(
                    text = artPiece.creditLine,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = mediumPadding)
                )
            }

            Spacer(Modifier.height(smallPadding))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = smallPadding,
                color = DividerDefaults.color.copy(alpha = 0.3f),
            )

            Spacer(Modifier.height(smallPadding))

            DetailViewCollapsibleInfoSection(
                label = "Description",
                expanded = isDetailsSectionExpanded,
                onExpandedChange = { isDetailsSectionExpanded = it },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(smallPadding),
                        modifier = Modifier.padding(horizontal = mediumPadding, vertical = smallPadding)
                    ) {
                        Text(
                            text = artPiece.department.takeUnless { it.isNullOrBlank() } ?: "No Department Found",
                            fontSize = 16.sp,
                        )

                        Text(
                            text = artPiece.constituentResponses?.firstOrNull()?.name ?: "No Constituent Found",
                            fontSize = 16.sp,
                        )
                    }
                }
            )

            DetailViewCollapsibleInfoSection(
                label = "Historical Context",
                expanded = isHistoricalSectionExpanded,
                onExpandedChange = { isHistoricalSectionExpanded = it },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(smallPadding),
                        modifier = Modifier.padding(horizontal = mediumPadding, vertical = smallPadding)
                    ) {
                        Text(
                            text = artPiece.period.takeUnless { it.isNullOrBlank() } ?: "No Period Found",
                            fontSize = 16.sp,
                        )

                        Text(
                            text = artPiece.dynasty.takeUnless { it.isNullOrBlank() } ?: "No Dynasty Found",
                            fontSize = 16.sp,
                        )
                    }
                }
            )

            DetailViewCollapsibleInfoSection(
                label = "Artist",
                expanded = isArtistsSectionExpanded,
                onExpandedChange = { isArtistsSectionExpanded = it },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(smallPadding),
                        modifier = Modifier.padding(horizontal = mediumPadding, vertical = smallPadding)
                    ) {
                        Text(
                            text = artPiece.artistDisplayName.takeUnless { it.isNullOrBlank() } ?: "No Artist Found",
                            fontSize = 16.sp,
                        )

                        Text(
                            text = artPiece.artistNationality.takeUnless { it.isNullOrBlank() } ?: "No Artist Nationality Found",
                            fontSize = 16.sp,
                        )
                    }
                }
            )

            DetailViewCollapsibleInfoSection(
                label = "Technical Information",
                expanded = isTechnicalInformationSectionExpanded,
                onExpandedChange = { isTechnicalInformationSectionExpanded = it },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(smallPadding),
                        modifier = Modifier.padding(horizontal = mediumPadding, vertical = smallPadding)
                    ) {
                        Text(
                            text = artPiece.objectBeginDate?.toString() ?: "No Begin Date Found",
                            fontSize = 16.sp,
                        )

                        Text(
                            text = artPiece.objectEndDate?.toString() ?: "No End Date Found",
                            fontSize = 16.sp,
                        )
                    }
                }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = smallPadding,
                color = DividerDefaults.color.copy(alpha = 0.3f),
            )

            Spacer(Modifier.height(largePadding))

            // TODO: Add more content here
        }
    }
}