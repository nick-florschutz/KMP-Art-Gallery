package kmp.fbk.kmpartgallery.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kmp.fbk.kmpartgallery.ViewModelState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewScreenViewModel(
    artPieceLocalId: Long,
    private val detailViewScreenRepository: DetailViewScreenRepository,
) : ViewModel() {

    companion object {
        private const val ERROR_DELAY_MS = 3_000L
    }

    private val _uiState = MutableStateFlow<ViewModelState>(ViewModelState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _artPiece = MutableStateFlow<ArtPiece?>(null)
    val artPiece = _artPiece.asStateFlow()

    init {
        collectOnArtPiece(artPieceLocalId)
        observeArtPieceForUiState()
    }

    fun determineWhichImageToUse(): String? {
        return _artPiece.value?.primaryImage
            ?: _artPiece.value?.primaryImageSmall
            ?: _artPiece.value?.additionalImages?.firstOrNull()
    }

    private fun collectOnArtPiece(artPieceLocalId: Long) {
        viewModelScope.launch {
            detailViewScreenRepository.getArtPieceByIdFlow(artPieceLocalId).collectLatest {
                _artPiece.emit(it)
            }
        }
    }

    private fun observeArtPieceForUiState() {
        viewModelScope.launch {
            _artPiece.collectLatest {
                if (it != null) {
                    _uiState.emit(ViewModelState.Success(Unit))
                } else {
                    delay(ERROR_DELAY_MS)
                    _uiState.emit(ViewModelState.Error(Throwable("No Art Piece Found")))
                }
            }
        }
    }

}