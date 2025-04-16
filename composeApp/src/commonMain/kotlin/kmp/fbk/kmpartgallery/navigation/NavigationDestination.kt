package kmp.fbk.kmpartgallery.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

sealed class NavigationDestination {
     companion object {
         private const val STRING_ROUTE_PATTERN = "kmp.fbk.kmpartgallery.navigation.NavigationDestination."

         fun fromString(stringRoute: String?): NavigationDestination? {
             return when (stringRoute?.removePrefix(STRING_ROUTE_PATTERN)) {
                 Home.navigationKey -> Home
                 Explore.navigationKey -> Explore
                 Collections.navigationKey -> Collections
                 Artists.navigationKey -> Artists
                 DetailView.navigationKey -> DetailView(null)
                 else -> null
             }
         }
     }

    abstract val screenLabel: String

    @Composable
    open fun getTopBarContent(): Unit = Unit

    /**
     * Allows the contents of the screen to expand to the top edge of the screen.
     */
    open val expandToEdgeOfScreen: Boolean = false

    open val showBottonNavigator: Boolean = true

    @Serializable
    data object Home : NavigationDestination() {
        val navigationKey = this::class.simpleName

        override val screenLabel: String
            get() = "Home"
    }

    @Serializable
    data object Explore : NavigationDestination() {
        val navigationKey = this::class.simpleName
        override val screenLabel: String
            get() = "Explore"
    }

    @Serializable
    data object Collections : NavigationDestination() {
        val navigationKey = this::class.simpleName
        override val screenLabel: String
            get() = "Collections"
    }

    @Serializable
    data object Artists : NavigationDestination() {
        val navigationKey = this::class.simpleName
        override val screenLabel: String
            get() = "Artists"
    }

    @Serializable
    data class DetailView(val artPieceLocalId: Long?) : NavigationDestination() {
        companion object {
            val navigationKey = this::class.simpleName
        }

        override val showBottonNavigator = false

        override val screenLabel: String
            get() = "Detail View"
    }
}