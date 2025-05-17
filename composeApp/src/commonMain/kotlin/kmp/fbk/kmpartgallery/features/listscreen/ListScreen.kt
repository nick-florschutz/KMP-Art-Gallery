package kmp.fbk.kmpartgallery.features.listscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kmp.fbk.kmpartgallery.ViewModelState
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.mediumLargePadding
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.navigation.NavigationDestination
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.search.CustomSearchView
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.sort_down
import kmpartgallery.composeapp.generated.resources.the_metropolitan_museum_of_art_logo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreen(navController: NavController) {
    val listScreenRepository = KoinPlatform.getKoin().get<ListScreenRepository>()
    val viewModel = viewModel {
        ListScreenViewModel(
            listScreenRepository = listScreenRepository,
        )
    }

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val featuredImagesListState by viewModel.featuredImagesListState.collectAsStateWithLifecycle()
//    val featuredImages by viewModel.featuredImagesList.collectAsStateWithLifecycle()
    val departments by viewModel.departmentsList.collectAsStateWithLifecycle()
    val artPieces by viewModel.artPieceResponseList.collectAsStateWithLifecycle()

    val selectedDepartment by viewModel.selectedDepartment.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState {
        ((featuredImagesListState as? FeaturedImagesListState.Success)?.featuredImages?.size ?: 0)
    }

    val scrollState = rememberScrollState()
    val mainListState = rememberLazyListState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val showScrollToTopButton by remember { derivedStateOf { mainListState.firstVisibleItemIndex > 0 } }

    var isSortFilterMenuOpen by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val navigationDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val platformContext = LocalPlatformContext.current
    val focusManager = LocalFocusManager.current

    ModalNavigationDrawer(
        drawerState = navigationDrawerState,
        drawerContent = {
            Surface(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
            ) {
                Column(Modifier.padding(mediumPadding)) {
                    Text("HELLO!")
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                ListScreenTopBar(
                    searchQuery = searchQuery,
                    focusManager = focusManager,
                    viewModel = viewModel,
                    onMenuButtonClick = {
                        coroutineScope.launch {
                            navigationDrawerState.open()
                        }
                    },
                    onStartIconClick = {},
                )
            },
            bottomBar = {},
            floatingActionButton = {

                Column(
                    verticalArrangement = Arrangement.spacedBy(smallPadding),
                ) {
                    AnimatedVisibility(
                        visible = isSortFilterMenuOpen,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(0.75f)
                            .align(Alignment.Start)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(smallPadding),
                                )
                                .padding(smallPadding)
                        ) {
                            Text("Sort & Filter")
                        }
                    }

                    FloatingActionButton(
                        onClick = {
                            isSortFilterMenuOpen = !isSortFilterMenuOpen
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
                        },
                        modifier = Modifier.align(Alignment.End)
                    )
                }


            },
            modifier = Modifier
                .fillMaxSize()
                .clickable(interactionSource = null, indication = null) {
                    focusManager.clearFocus(force = true)
                }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    state = mainListState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        when (featuredImagesListState) {
                            FeaturedImagesListState.Loading -> {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            is FeaturedImagesListState.Error -> {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                ) {
                                    Text(text = "No Featured Images Available.")
                                }
                            }

                            is FeaturedImagesListState.Success -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                ) {
                                    HorizontalPager(
                                        state = pagerState,
                                        pageContent = { page ->
                                            val featuredImagesList by remember((featuredImagesListState as FeaturedImagesListState.Success).featuredImages) {
                                                mutableStateOf((featuredImagesListState as FeaturedImagesListState.Success).featuredImages)
                                            }
                                            AsyncImage(
                                                model = ImageRequest.Builder(platformContext)
                                                    .data(featuredImagesList[page].image)
                                                    .build(),
                                                contentDescription = null,
                                                placeholder = rememberVectorPainter(Icons.Default.FileDownload),
                                                fallback = rememberVectorPainter(Icons.Default.Error),
                                                error = rememberVectorPainter(Icons.Default.Error),
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clickable {
                                                        navController.navigate(
                                                            NavigationDestination.DetailView(
                                                                artPieceLocalId = featuredImagesList[page].localId
                                                            )
                                                        )
                                                    }
                                            )
                                        }
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            extraSmallPadding
                                        ),
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
                            }
                        }
                    }


                    stickyHeader {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = mediumPadding),
                            horizontalArrangement = Arrangement.spacedBy(smallPadding),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .wrapContentSize()
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            item {
                                FilterChip(
                                    selected = selectedDepartment == null,
                                    onClick = {
                                        coroutineScope.launch {
                                            lazyStaggeredGridState.scrollToItem(0)
                                        }
                                        viewModel.onAllDepartmentsSelected()
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                                        selectedLabelColor = MaterialTheme.colorScheme.surface,
                                    ),
                                    shape = RoundedCornerShape(4.dp),
                                    label = { Text(text = "All") },
                                )
                            }

                            items(departments) { department ->
                                department.displayName?.let { departmentName ->
                                    FilterChip(
                                        selected = selectedDepartment == departmentName,
                                        onClick = {
                                            coroutineScope.launch {
                                                lazyStaggeredGridState.scrollToItem(0)
                                            }
                                            viewModel.getArtPiecesByDepartment(departmentName)
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                                            selectedLabelColor = MaterialTheme.colorScheme.surface,
                                        ),
                                        shape = RoundedCornerShape(4.dp),
                                        label = { Text(text = departmentName) },
                                    )
                                }

                            }
                        }
                    }

                    item {
                        if (artPieces.isEmpty() && uiState !is ViewModelState.Loading) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            ) {
                                Text("No Art Pieces Available")
                            }
                        } else {
                            ArtPieceStaggeredGrid(
                                artPieces = artPieces,
                                lazyStaggeredGridState = lazyStaggeredGridState,
                                mainScreenScrollState = scrollState,
                                navController = navController,
                                mainListState = mainListState,
                            )
                        }
                    }

                }

                AnimatedVisibility(
                    visible = showScrollToTopButton,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .then(
                            if (showScrollToTopButton) Modifier.systemBarsPadding() else Modifier
                        )
                        .padding(mediumPadding)
                ) {
                    FloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                lazyStaggeredGridState.animateScrollToItem(0)
//                         scrollState.animateScrollTo(0)
                                mainListState.animateScrollToItem(0)
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

            if (uiState is ViewModelState.Loading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                        .padding(paddingValues)
                ) {
                    CircularProgressIndicator()
                }
            }

            if (isSortFilterMenuOpen) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            isSortFilterMenuOpen = false
                        }
                )
            }

        } /* End of LazyColumn */
    } /* End of ModalNavigationDrawer */
}

@Composable
fun ListScreenTopBar(
    searchQuery: String,
    focusManager: FocusManager,
    viewModel: ListScreenViewModel,
    onMenuButtonClick: () -> Unit,
    onStartIconClick: () -> Unit,
) {
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
                modifier = Modifier.size(26.dp)
                    .clickable { onStartIconClick() }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(mediumLargePadding),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                CustomSearchView(
                    searchText = searchQuery,
                    focusManager = focusManager,
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                )

                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                        .clickable { onMenuButtonClick() }
                )
            }
        }
    }
}