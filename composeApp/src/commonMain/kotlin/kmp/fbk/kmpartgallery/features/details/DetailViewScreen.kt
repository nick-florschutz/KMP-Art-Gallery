package kmp.fbk.kmpartgallery.features.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import kmp.fbk.kmpartgallery.getScreenHeight
import kmp.fbk.kmpartgallery.largePadding
import kmp.fbk.kmpartgallery.mediumFontSize
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.ImageViewer
import kmp.fbk.kmpartgallery.smallMediumPadding
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.artist
import kmpartgallery.composeapp.generated.resources.artist_begin_date
import kmpartgallery.composeapp.generated.resources.artist_display_bio
import kmpartgallery.composeapp.generated.resources.artist_end_date
import kmpartgallery.composeapp.generated.resources.artist_gender
import kmpartgallery.composeapp.generated.resources.artist_nationality
import kmpartgallery.composeapp.generated.resources.artist_url
import kmpartgallery.composeapp.generated.resources.artist_wikidata_url
import kmpartgallery.composeapp.generated.resources.begin_date
import kmpartgallery.composeapp.generated.resources.classification
import kmpartgallery.composeapp.generated.resources.constituent
import kmpartgallery.composeapp.generated.resources.department
import kmpartgallery.composeapp.generated.resources.description
import kmpartgallery.composeapp.generated.resources.dimensions
import kmpartgallery.composeapp.generated.resources.dynasty
import kmpartgallery.composeapp.generated.resources.end_date
import kmpartgallery.composeapp.generated.resources.error_loading_art_piece
import kmpartgallery.composeapp.generated.resources.excavation
import kmpartgallery.composeapp.generated.resources.historical_context
import kmpartgallery.composeapp.generated.resources.locale
import kmpartgallery.composeapp.generated.resources.location
import kmpartgallery.composeapp.generated.resources.locus
import kmpartgallery.composeapp.generated.resources.measurements
import kmpartgallery.composeapp.generated.resources.medium
import kmpartgallery.composeapp.generated.resources.no_results_found
import kmpartgallery.composeapp.generated.resources.period
import kmpartgallery.composeapp.generated.resources.region
import kmpartgallery.composeapp.generated.resources.reign
import kmpartgallery.composeapp.generated.resources.repository
import kmpartgallery.composeapp.generated.resources.river
import kmpartgallery.composeapp.generated.resources.subregion
import kmpartgallery.composeapp.generated.resources.technical_information
import kmpartgallery.composeapp.generated.resources.unknown
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform

private const val DETAIL_CHIP_CHAR_LIMIT = 50

@OptIn(ExperimentalComposeUiApi::class)
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

    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    val showScrollToTopButton by remember { derivedStateOf { scrollState.value > 3 } }

    var isFullscreenImageOpen by rememberSaveable { mutableStateOf(false) }

    BackHandler {
        if (isFullscreenImageOpen) {
            isFullscreenImageOpen = false
            return@BackHandler
        }
        navController.popBackStack()
    }

    when (uiState) {
        is ViewModelState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = stringResource(Res.string.error_loading_art_piece))
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
           Box(Modifier.fillMaxSize()) {
               DetailViewScreenContent(
                   artPiece = artPieceValue,
                   viewModel = viewModel,
                   platformContext = platformContext,
                   navController = navController,
                   scrollState = scrollState,
                   onImageTapped = { isFullscreenImageOpen = true },
               )

               AnimatedVisibility(
                   visible = showScrollToTopButton,
                   enter = slideInVertically(initialOffsetY = {it}),
                   exit = slideOutVertically(targetOffsetY = {it}),
                   modifier = Modifier
                       .align(Alignment.BottomStart)
                       .then(
                           if (showScrollToTopButton) Modifier.systemBarsPadding() else Modifier
                       )
                       .padding(horizontal = mediumPadding)
               ) {
                   FloatingActionButton(
                       onClick = {
                           coroutineScope.launch {
                               scrollState.animateScrollTo(0)
                           }
                       },
                   ) {
                       Icon(
                           imageVector = Icons.Default.ArrowUpward,
                           contentDescription = null,
                       )
                   }
               }
           }
        }
    }

    AnimatedVisibility(
        visible = isFullscreenImageOpen,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable(interactionSource = MutableInteractionSource(), indication = null) {}
        ) {
            ImageViewer(
                modifier = Modifier.fillMaxSize(),
                image = viewModel.determineWhichImageToUse().orEmpty(),
            )

            IconButton(
                onClick = { isFullscreenImageOpen = false },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .systemBarsPadding()
                    .padding(start = mediumPadding)
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
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailViewScreenContent(
    artPiece: ArtPiece,
    viewModel: DetailViewScreenViewModel,
    platformContext: PlatformContext,
    navController: NavController,
    scrollState: ScrollState,
    onImageTapped: () -> Unit,
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
            .verticalScroll(scrollState)
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
                    .clickable {
                        onImageTapped()
                    }
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .systemBarsPadding()
                    .padding(start = mediumPadding)
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
                text = artPiece.title ?: stringResource(Res.string.no_results_found),
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
                   text = artPiece.artistDisplayName?.takeIf { it.isNotBlank() }  ?: stringResource(Res.string.unknown),
                   fontSize = mediumFontSize,
                   color = if (artPiece.artistDisplayName.isNullOrBlank()) Color.Gray else Color.Blue,
                   modifier = Modifier.weight(weight = 0.4f, fill = false)
               )

               Icon(
                   imageVector = Icons.Default.Circle,
                   tint = Color.Gray,
                   contentDescription = null,
                   modifier = Modifier.size(6.dp)
                       .weight(0.1f, fill = false)
               )

               Text(
                   text = artPiece.objectDate?.takeIf { it.isNotBlank() } ?: stringResource(Res.string.unknown),
                   fontSize = mediumFontSize,
                   modifier = Modifier.weight(0.4f)
               )
           }

            // Quick info chip group
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
                    fontSize = mediumFontSize,
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
                label = stringResource(Res.string.description),
                expanded = isDetailsSectionExpanded,
                onExpandedChange = { isDetailsSectionExpanded = it },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(smallMediumPadding),
                        modifier = Modifier
                            .background(DividerDefaults.color.copy(alpha = 0.2f))
                            .padding(horizontal = mediumPadding, vertical = smallPadding)
                    ) {
                        if (!artPiece.title.isNullOrBlank()) {
                            val introduction = "${artPiece.title} - ${artPiece.artistPrefix.orEmpty()} ${artPiece.artistDisplayName?.ifBlank { "Unknown" }}"
                            Text(
                                text = introduction,
                                fontSize = mediumFontSize,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(Modifier.height(smallPadding))
                        }

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.medium),
                            valueText = artPiece.medium.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.department),
                            valueText = artPiece.department.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.constituent),
                            valueText = artPiece.constituentResponses?.firstOrNull()?.name.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.classification),
                            valueText = artPiece.classification.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.repository),
                            valueText = artPiece.repository.orEmpty(),
                            defaultEmptyText = stringResource(Res.string.unknown),
                        )
                    }
                }
            )

            DetailViewCollapsibleInfoSection(
                label = stringResource(Res.string.historical_context),
                expanded = isHistoricalSectionExpanded,
                onExpandedChange = { isHistoricalSectionExpanded = it },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(smallPadding),
                        modifier = Modifier
                            .background(DividerDefaults.color.copy(alpha = 0.2f))
                            .padding(horizontal = mediumPadding, vertical = smallPadding)
                    ) {
                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.period),
                            valueText = artPiece.period.orEmpty(),
                            defaultEmptyText = stringResource(Res.string.unknown),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.reign),
                            valueText = artPiece.reign.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.dynasty),
                            valueText = artPiece.dynasty.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.begin_date),
                            valueText = artPiece.objectBeginDate?.toString().orEmpty(),
                            defaultEmptyText = stringResource(Res.string.unknown),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.end_date),
                            valueText = artPiece.objectEndDate?.toString().orEmpty(),
                            defaultEmptyText = stringResource(Res.string.unknown),
                        )

                        val locationDescription = "${artPiece.geographyType} ${artPiece.county} [${artPiece.city} ${artPiece.state}, ${artPiece.country}]"
                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.location),
                            valueText = locationDescription,
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.region),
                            valueText = artPiece.region.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.subregion),
                            valueText = artPiece.subregion.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.locale),
                            valueText = artPiece.locale.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.locus),
                            valueText = artPiece.locus.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.excavation),
                            valueText = artPiece.excavation.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.river),
                            valueText = artPiece.river.orEmpty(),
                        )
                    }
                }
            )

            DetailViewCollapsibleInfoSection(
                label = stringResource(Res.string.artist),
                expanded = isArtistsSectionExpanded,
                onExpandedChange = { isArtistsSectionExpanded = it },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(smallPadding),
                        modifier = Modifier
                            .background(DividerDefaults.color.copy(alpha = 0.2f))
                            .padding(horizontal = mediumPadding, vertical = smallPadding)
                    ) {
                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.artist),
                            valueText = artPiece.artistDisplayName.orEmpty(),
                            defaultEmptyText = stringResource(Res.string.unknown),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.artist_display_bio),
                            valueText = artPiece.artistDisplayBio.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.artist_nationality),
                            valueText = artPiece.artistNationality.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.artist_begin_date),
                            valueText = artPiece.artistBeginDate.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.artist_end_date),
                            valueText = artPiece.artistEndDate.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.artist_gender),
                            valueText = artPiece.artistGender.orEmpty(),
                            defaultEmptyText = stringResource(Res.string.unknown),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.artist_wikidata_url),
                            valueText = artPiece.artistWikidataURL.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.artist_url),
                            valueText = artPiece.artistULANURL.orEmpty(),
                        )
                    }
                }
            )

            DetailViewCollapsibleInfoSection(
                label = stringResource(Res.string.technical_information),
                expanded = isTechnicalInformationSectionExpanded,
                onExpandedChange = { isTechnicalInformationSectionExpanded = it },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(smallPadding),
                        modifier = Modifier
                            .background(DividerDefaults.color.copy(alpha = 0.2f))
                            .padding(horizontal = mediumPadding, vertical = smallPadding)
                    ) {
                        DetailedSectionTextWithLabel(
                            stringResource(Res.string.medium),
                            valueText = artPiece.medium.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.dimensions),
                            valueText = artPiece.dimensions.orEmpty(),
                        )

                        DetailedSectionTextWithLabel(
                            labelText = stringResource(Res.string.measurements),
                            valueText = artPiece.measurements?.firstOrNull()?.printMeasurements().orEmpty(),
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
            Spacer(Modifier.height(largePadding))
        }
    }
}

@Composable
private fun DetailedSectionTextWithLabel(
    labelText: String,
    valueText: String,
    defaultEmptyText: String = "--",
) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                append("$labelText: ")
            }
            append(
                valueText.ifBlank { defaultEmptyText },
            )
        },
        fontSize = mediumFontSize,
    )
}