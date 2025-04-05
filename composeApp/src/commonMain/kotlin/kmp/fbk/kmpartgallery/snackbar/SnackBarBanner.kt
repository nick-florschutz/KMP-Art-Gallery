package kmp.fbk.kmpartgallery.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kmp.fbk.kmpartgallery.errorBannerBackgroundColor
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.hyperlinkBlue
import kmp.fbk.kmpartgallery.informationBannerColor
import kmp.fbk.kmpartgallery.lightBlue
import kmp.fbk.kmpartgallery.mediumFontSize
import kmp.fbk.kmpartgallery.smallMediumFontSize
import kmp.fbk.kmpartgallery.smallPadding
import kmp.fbk.kmpartgallery.successBannerColor
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.dismiss
import org.jetbrains.compose.resources.stringResource

@Composable
fun SnackBarBanner(
    bannerInfo: BannerInformation,
    hasDismissAction: Boolean = false,
    onDismiss: () -> Unit = {},
    textAndIconColorTheme: Color = Color.White,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (hasDismissAction) {
            Arrangement.SpaceBetween
        } else {
            Arrangement.spacedBy(smallPadding)
        },
        modifier = modifier
            .background(
                color = bannerInfo.backgroundColor,
                shape = RoundedCornerShape(extraSmallPadding),
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(extraSmallPadding),
            ),
    ) {
        Icon(
            imageVector = bannerInfo.icon,
            tint = textAndIconColorTheme,
            contentDescription = null,
            modifier = Modifier
                .padding(start = smallPadding, top = smallPadding, bottom = smallPadding)
        )

        Text(
            text = bannerInfo.messageInternal,
            color = textAndIconColorTheme,
            fontSize = mediumFontSize,
            maxLines = 1,
        )

        if (hasDismissAction) {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.wrapContentSize()
            ) {
                Text(
                    text = stringResource(Res.string.dismiss),
                    fontSize = smallMediumFontSize,
                    color = if (isSystemInDarkTheme()) hyperlinkBlue else lightBlue,
                    modifier = Modifier
                        .padding(smallPadding)
                        .clickable { onDismiss() }
                )
            }
        }
    }
}

/**
 * Class containing the different supported versions of a banner. Error, Information, and
 * Success are the currently supported types. I would like to be able to use this with the
 * SnackBar Host so that I can show these as SnackBars.
 */
sealed class BannerInformation(
    val backgroundColor: Color,
    val icon: ImageVector,
    val messageInternal: String,
) {
    data class Error(
        val message: String,
    ): BannerInformation(
        backgroundColor = errorBannerBackgroundColor,
        icon = Icons.Default.Error,
        messageInternal = message,
    )

    data class Information(
        val message: String,
    ): BannerInformation(
        backgroundColor = informationBannerColor,
        icon = Icons.Default.Info,
        messageInternal = message,
    )

    data class Success(
        val message: String,
    ): BannerInformation(
        backgroundColor = successBannerColor,
        icon = Icons.Default.Check,
        messageInternal = message,
    )
}