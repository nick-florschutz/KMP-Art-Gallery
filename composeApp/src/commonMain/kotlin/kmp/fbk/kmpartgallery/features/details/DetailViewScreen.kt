package kmp.fbk.kmpartgallery.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import kmp.fbk.kmpartgallery.mediumPadding
import kmp.fbk.kmpartgallery.smallPadding
import org.koin.mp.KoinPlatform

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
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                text = artPiece.title ?: "No Art Piece Found",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )

           Row(
               horizontalArrangement = Arrangement.spacedBy(extraSmallPadding),
               verticalAlignment = Alignment.CenterVertically,
           ) {
               Text(
                   text = artPiece.artistDisplayName?.takeIf { it.isNotBlank() }  ?: "Unknown",
                   fontSize = 18.sp,
                   color = if (artPiece.artistDisplayName.isNullOrBlank()) Color.Gray else Color.Blue,
                   modifier = Modifier
               )

               Icon(
                   imageVector = Icons.Default.Circle,
                   tint = Color.Gray,
                   contentDescription = null,
                   modifier = Modifier.size(8.dp)
               )

               Text(
                   text = artPiece.objectDate?.takeIf { it.isNotBlank() } ?: "Unknown",
                   fontSize = 18.sp,
                   modifier = Modifier
               )
           }
        }
    }
}