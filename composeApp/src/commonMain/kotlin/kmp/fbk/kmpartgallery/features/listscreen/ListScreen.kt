package kmp.fbk.kmpartgallery.features.listscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.LocalPlatformContext
import kmp.fbk.kmpartgallery.networking.download.DepartmentsDownloadMachine
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

        ArtPieceStaggeredGrid(artPieces = artPieces)

    }

}