package kmp.fbk.kmpartgallery.features.listscreen

sealed class ViewModelState {
    data object Loading : ViewModelState()

    data class Success(val data: Any) : ViewModelState()

    data class Error(val exception: Throwable) : ViewModelState()
}