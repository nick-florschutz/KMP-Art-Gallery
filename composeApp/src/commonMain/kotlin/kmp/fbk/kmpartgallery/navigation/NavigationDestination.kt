package kmp.fbk.kmpartgallery.navigation

import kotlinx.serialization.Serializable

sealed class NavigationDestination {
     companion object {
         private const val STRING_ROUTE_PATTERN = "kmp.fbk.kmpartgallery.navigation.NavigationDestination."

         fun fromString(stringRoute: String?): NavigationDestination {
             return when (stringRoute?.removePrefix(STRING_ROUTE_PATTERN)) {
                 Home.screenLabel -> Home
                 Explore.screenLabel -> Explore
                 Collections.screenLabel -> Collections
                 Artists.screenLabel -> Artists
                 else -> throw IllegalArgumentException("Unknown destination")
             }
         }
     }

    abstract val screenLabel: String

    @Serializable
    data object Home : NavigationDestination() {
        override val screenLabel: String
            get() = "Home"
    }

    @Serializable
    data object Explore : NavigationDestination() {
        override val screenLabel: String
            get() = "Explore"
    }

    @Serializable
    data object Collections : NavigationDestination() {
        override val screenLabel: String
            get() = "Collections"
    }

    @Serializable
    data object Artists : NavigationDestination() {
        override val screenLabel: String
            get() = "Artists"
    }

}