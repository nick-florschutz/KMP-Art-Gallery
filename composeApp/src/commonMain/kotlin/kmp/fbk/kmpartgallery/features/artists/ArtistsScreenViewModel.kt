package kmp.fbk.kmpartgallery.features.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kmp.fbk.kmpartgallery.ViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtistsScreenViewModel(
    private val repository: ArtistsScreenRepository,
): ViewModel() {

    private val _state = MutableStateFlow<ViewModelState>(ViewModelState.Loading)
    val state = _state.asStateFlow()

    init {
        loadArtistsList()
    }

    private fun loadArtistsList() {
        viewModelScope.launch {
            _state.value = ViewModelState.Loading
            val artistsList = repository.getAllArtists()
            _state.value = ViewModelState.Success(artistsList)
        }
    }

}