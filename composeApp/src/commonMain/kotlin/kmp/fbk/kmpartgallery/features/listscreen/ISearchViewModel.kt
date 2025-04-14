package kmp.fbk.kmpartgallery.features.listscreen

interface ISearchViewModel {
    fun onSearchTextChange(query: String, additionalArgs: String? = null)
}