package kmp.fbk.kmpartgallery.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.runtime.Composable
import kmp.fbk.kmpartgallery.reusable_ui_compomenents.SimpleTopAppBar
import kotlinx.serialization.Serializable

sealed class NavigationDestination {
     companion object {
         private const val STRING_ROUTE_PATTERN = "kmp.fbk.kmpartgallery.navigation.NavigationDestination."

         fun fromString(stringRoute: String?): NavigationDestination? {
             return when (stringRoute?.removePrefix(STRING_ROUTE_PATTERN)) {
                 Home.navigationKey -> Home
                 Explore.navigationKey -> Explore
                 Collections.navigationKey -> Collections
                 CollectionByDepartment.navigationKey -> CollectionByDepartment(null)
                 Artists.navigationKey -> Artists
                 DetailView.navigationKey -> DetailView(null)
                 else -> null
             }
         }
     }

    abstract val screenLabel: String

    @Composable
    open fun getTopBarContent(): Unit = SimpleTopAppBar()

    /**
     * Allows the contents of the screen to expand to the top edge of the screen.
     */
    open val expandToEdgeOfScreen: Boolean = false

    /**
     * Determines whether or not the screen shows the [kmp.fbk.kmpartgallery.reusable_ui_compomenents.MainBottomNavigationBarKt.MainBottomNavigationBar].
     */
    open val showBottomNavigator: Boolean = true

    @Serializable
    data object Home : NavigationDestination() {
        val navigationKey = this::class.simpleName

        @Composable
        override fun getTopBarContent() = Unit

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

        @Composable
        override fun getTopBarContent() = Unit

        override val screenLabel: String
            get() = "Collections"
    }

    @Serializable
    data class CollectionByDepartment(
        val department: String?,
    ): NavigationDestination() {
        companion object {
            val navigationKey = this::class.simpleName
        }

        @Composable
        override fun getTopBarContent() = Unit

        override val showBottomNavigator: Boolean = false

        override val screenLabel: String
            get() = "Collections By Department"
    }

    @Serializable
    data object Artists : NavigationDestination() {
        val navigationKey = this::class.simpleName

        @Composable
        override fun getTopBarContent() = Unit

        override val screenLabel: String
            get() = "Artists"
    }

    @Serializable
    data class DetailView(val artPieceLocalId: Long?) : NavigationDestination() {
        companion object {
            val navigationKey = this::class.simpleName
        }

        override val showBottomNavigator = false

        override val screenLabel: String
            get() = "Detail View"
    }
}