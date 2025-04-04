package kmp.fbk.kmpartgallery

import androidx.compose.ui.window.ComposeUIViewController
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kmp.fbk.kmpartgallery.di.appModules
import kmp.fbk.kmpartgallery.di.getRoomDatabaseModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController { App() }

fun debugBuild() {
    Napier.base(DebugAntilog())
}

fun initKoin() {
    val iosModule = listOf(
        getRoomDatabaseModule
    )

    startKoin {
        modules(appModules() + iosModule)
    }
}