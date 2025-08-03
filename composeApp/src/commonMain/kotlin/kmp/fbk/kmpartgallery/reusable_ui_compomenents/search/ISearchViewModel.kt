package kmp.fbk.kmpartgallery.reusable_ui_compomenents.search

interface ISearchViewModel {
    fun onSearchTextChange(query: String, additionalArgs: String? = null)
}