package kmp.fbk.kmpartgallery.app_theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) {
            darkThemeColorScheme
        } else {
            lightThemeColorScheme
        },
        content = {
            Surface(modifier = Modifier.wrapContentSize()) {
                content()
            }
        }
    )
}

val lightThemeColorScheme = lightColorScheme()

val darkThemeColorScheme = darkColorScheme()