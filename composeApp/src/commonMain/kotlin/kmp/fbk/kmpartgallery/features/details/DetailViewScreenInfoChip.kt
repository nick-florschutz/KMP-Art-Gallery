package kmp.fbk.kmpartgallery.features.details

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.smallPadding

@Composable
fun DetailViewScreenInfoChip(
    label: String,
) {
    AssistChip(
        enabled = false,
        onClick = {},
        colors = AssistChipDefaults.assistChipColors(
            disabledContainerColor = Color.LightGray.copy(alpha = 0.5f),
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = RoundedCornerShape(50),
        border = null,
        label = {
            Text(
                text = label,
                modifier = Modifier
                    .padding(
                        vertical = extraSmallPadding,
                        horizontal = smallPadding,
                    )
            )
        },
    )
}