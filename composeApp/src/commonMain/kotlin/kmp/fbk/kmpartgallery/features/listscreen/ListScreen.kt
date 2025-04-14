package kmp.fbk.kmpartgallery.features.listscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.largePadding
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.networking.download.ArtPieceDownloadMachine
import kmp.fbk.kmpartgallery.networking.download.DepartmentsDownloadMachine
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.sort_down
import kmpartgallery.composeapp.generated.resources.the_metropolitan_museum_of_art_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
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

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val featuredImages by viewModel.featuredImagesList.collectAsStateWithLifecycle()
    val departments by viewModel.departmentsList.collectAsStateWithLifecycle()
    val artPieces by viewModel.artPieceResponseList.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState {
        featuredImages.size
    }

    val scrollState = rememberScrollState()
    val platformContext = LocalPlatformContext.current

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(mediumPadding)
                ) {

                    Icon(
                        painter = painterResource(Res.drawable.the_metropolitan_museum_of_art_logo),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                  Row(
//                      horizontalArrangement = Arrangement.spacedBy(smallPadding),
                      verticalAlignment = Alignment.CenterVertically,
                      modifier = Modifier
                  ) {
                      DockedSearchBar(
                          inputField = {
                              SearchBarDefaults.InputField(
                                  query = "",
                                  onQueryChange = {},
                                  onSearch = {},
                                  placeholder = {
                                      Text(text = "Search Artworks...")
                                  },
                                  expanded = false,
                                  onExpandedChange = {},
                                  leadingIcon = {
                                      Icon(
                                          imageVector = Icons.Default.Search,
                                          contentDescription = null,
                                      )
                                  }
                              )
                          },
                          shape = RoundedCornerShape(4.dp),
                          expanded = false,
                          onExpandedChange = {},
                          modifier = Modifier
                              .weight(0.9f)
                              .scale(0.7f)
//                              .alignBy {
//                                  it.measuredWidth
//                              }
                      ) {

                      }

                      Icon(
                          imageVector = Icons.Default.Menu,
                          contentDescription = null,
                          modifier = Modifier.size(24.dp)
                              .weight(0.1f)
                      )
                  }
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
                        Icon(
                            imageVector = Icons.Default.CollectionsBookmark,
                            contentDescription = null
                        )
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
                        Text(text = "Artists")
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

        if (uiState is ViewModelState.Loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    pageContent = { page ->
                        AsyncImage(
                            model = ImageRequest.Builder(platformContext)
                                .data(featuredImages[page])
                                .build(),
                            contentDescription = null,
                            placeholder = rememberVectorPainter(Icons.Default.FileDownload),
                            fallback = rememberVectorPainter(Icons.Default.Error),
                            error = rememberVectorPainter(Icons.Default.Error),
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(extraSmallPadding),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = smallPadding)
                ) {
                    repeat(pagerState.pageCount) {
                        Icon(
                            imageVector = Icons.Default.Circle,
                            tint = if (pagerState.currentPage == it) Color.White else Color.LightGray,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
            LazyRow(
                contentPadding = PaddingValues(horizontal = mediumPadding),
                horizontalArrangement = Arrangement.spacedBy(smallPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                item {
                    FilterChip(
                        selected = true,
                        onClick = { /*TODO*/ },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                            selectedLabelColor = MaterialTheme.colorScheme.surface,
                        ),
                        shape = RoundedCornerShape(4.dp),
                        label = {
                            Text(text = "All")
                        }
                    )
                }

                items(departments) { department ->
                    department.displayName?.let { departmentName ->
                        FilterChip(
                            selected = false,
                            onClick = { /*TODO*/ },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                                selectedLabelColor = MaterialTheme.colorScheme.surface,
                            ),
                            shape = RoundedCornerShape(4.dp),
                            label = {
                                Text(text = departmentName)
                            }
                        )
                    }

                }
            }

            ArtPieceStaggeredGrid(artPieces = artPieces, mainScreenScrollState = scrollState)

        }
    }
}