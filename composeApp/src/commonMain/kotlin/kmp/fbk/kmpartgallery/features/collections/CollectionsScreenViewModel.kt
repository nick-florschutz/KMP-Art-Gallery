package kmp.fbk.kmpartgallery.features.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kmp.fbk.kmpartgallery.ViewModelState
import kmp.fbk.kmpartgallery.domain_models.ArtPiece
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CollectionsScreenViewModel(
    private val repository: CollectionsRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
): ViewModel() {

    private val _state = MutableStateFlow<ViewModelState>(ViewModelState.Loading)
    val state = _state.asStateFlow()

    fun loadArtPiecesAndDepartments() {
        viewModelScope.launch(defaultDispatcher) {
            _state.emit(ViewModelState.Loading)
            val artPiecesAndDepartments = repository.getAllArtPiecesWithDepartmentsMap()
            _state.emit(ViewModelState.Success(data = artPiecesAndDepartments))
        }
    }

    fun getArtPiecesByDepartment(department: String) {
        viewModelScope.launch(defaultDispatcher) {
            _state.emit(ViewModelState.Loading)
            val artPieces = repository.getAllArtPiecesByDepartment(department)
            _state.emit(ViewModelState.Success(data = artPieces))
        }
    }

}