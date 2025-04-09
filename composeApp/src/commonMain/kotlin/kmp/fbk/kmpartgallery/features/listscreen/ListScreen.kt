package kmp.fbk.kmpartgallery.features.listscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.constrain
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.Image
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.networking.download.DepartmentsDownloadMachine
import kmp.fbk.kmpartgallery.networking.response_data_models.ArtPieceResponse
import kmp.fbk.kmpartgallery.smallPadding
import kmp.fbk.kmpartgallery.snackbar.BannerInformation
import kmp.fbk.kmpartgallery.snackbar.AppSnackBarBannerHostState
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform

@Composable
fun ListScreen() {

    val listScreenRepository = KoinPlatform.getKoin().get<ListScreenRepository>()
    val departmentsDownloadMachine = KoinPlatform.getKoin().get<DepartmentsDownloadMachine>()
    val viewModel = viewModel {
        ListScreenViewModel(
            listScreenRepository = listScreenRepository,
            departmentsDownloadMachine = departmentsDownloadMachine,
        )
    }

    val coroutineScope = rememberCoroutineScope()
    val platformContext = LocalPlatformContext.current

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val departments by viewModel.departmentsList.collectAsStateWithLifecycle()
    val artPieces by viewModel.artPieceResponseList.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState is ViewModelState.Loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding()
            ) {
//            items(departments) { department ->
//                Column(
//                    modifier = Modifier.clickable {
////                        viewModel.insertDepartmentIntoDb(department.toDepartment())
//
//                        coroutineScope.launch {
//                            AppSnackBarBannerHostState.showSnackBar(
//                                BannerInformation.Information(
//                                    message = department.displayName
//                                )
//                            )
//                        }
//
//                    }
//                ) {
//                    Text(text = department.displayName ?: "--")
//                    Text(text = "Department ID: ${department.departmentId}")
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                if (departments.indexOf(department) != departments.lastIndex) {
//                    HorizontalDivider()
//                }
//            }

                items(artPieces) { artPiece ->

                    Box() {
                        if (artPiece.primaryImage.isNullOrEmpty()) {
                            Text("No Image Available...", Modifier.align(Alignment.Center))
                        } else {
                            AsyncImage(
                                model = ImageRequest.Builder(platformContext)
                                    .data(artPiece.primaryImage)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .heightIn(min = 300.dp)
                                    .padding(smallPadding)
                                    .clickable {
                                        coroutineScope.launch {
                                            AppSnackBarBannerHostState.showSnackBar(
                                                BannerInformation.Information(
                                                    message = artPiece.title ?: "--"
                                                )
                                            )
                                        }
                                    }
                            )
                        }

                       Box(
                           modifier = Modifier
                               .align(Alignment.BottomStart)
                               .padding(mediumPadding)
                       ) {

                           Column(
                               modifier = Modifier
                                   .background(Color.Black.copy(alpha = 0.3f))
                                   .padding(smallPadding)
                           ) {
                               Text(
                                   text = artPiece.title ?: "--",
                                   color = MaterialTheme.colorScheme.inverseOnSurface
                               )
                               Text(
                                   text = artPiece.artistDisplayName ?: "Unknown",
                                   color = MaterialTheme.colorScheme.inverseOnSurface
                               )
                           }
                       }
                    }

                }
            }
    }

}