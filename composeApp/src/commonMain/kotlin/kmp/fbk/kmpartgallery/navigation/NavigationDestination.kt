package kmp.fbk.kmpartgallery.navigation

import kotlinx.serialization.Serializable

sealed class NavigationDestination {
    abstract val screenLabel: String

    @Serializable
    data object Home : NavigationDestination() {
        override val screenLabel: String
            get() = "Home"

    }
}