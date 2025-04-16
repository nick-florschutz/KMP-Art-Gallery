package kmp.fbk.kmpartgallery.reusable_ui_compomenents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kmp.fbk.kmpartgallery.mediumPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.the_metropolitan_museum_of_art_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SimpleTopAppBar() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        ) {

            Icon(
                painter = painterResource(Res.drawable.the_metropolitan_museum_of_art_logo),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}