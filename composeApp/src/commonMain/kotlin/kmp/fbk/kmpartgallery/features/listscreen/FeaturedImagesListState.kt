package kmp.fbk.kmpartgallery.features.listscreen

sealed class FeaturedImagesListState {
    data object Loading : FeaturedImagesListState()
    data object Error : FeaturedImagesListState()
    data class Success(val featuredImages: List<String>) : FeaturedImagesListState()
}