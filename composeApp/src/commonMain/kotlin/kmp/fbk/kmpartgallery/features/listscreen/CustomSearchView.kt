package kmp.fbk.kmpartgallery.features.listscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp.fbk.kmpartgallery.extraSmallPadding
import kmp.fbk.kmpartgallery.smallPadding
import kmpartgallery.composeapp.generated.resources.Res
import kmpartgallery.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomSearchView(
    searchText: String,
    focusManager: FocusManager,
    viewModel: ISearchViewModel,
    additionalArgs: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = {
                viewModel.onSearchTextChange(
                    query = it,
                    additionalArgs = additionalArgs
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done,
            ), keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(force = true)
            }),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(smallPadding),
                    modifier = Modifier
                        .fillMaxWidth()
//                        .height(56.dp)
                        .background(
                            Color.LightGray.copy(alpha = 0.25f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = extraSmallPadding)
                ) {
                    AnimatedContent(
                        targetState = searchText.isNotEmpty(),
                        modifier = Modifier
                            .padding(start = smallPadding)
                    ) {
                        when (it) {
                            true -> {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear Search",
                                    modifier = Modifier.clickable {
                                        viewModel.onSearchTextChange(
                                            query = "",
                                            additionalArgs = additionalArgs
                                        )
                                    }
                                )
                            }

                            false -> {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                )
                            }
                        }
                    }

                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.wrapContentHeight()
                    ) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = searchText.isEmpty(),
                            enter = fadeIn(animationSpec = snap()),
                            exit = fadeOut(animationSpec = snap())
                        ) {
                            Text(text = stringResource(Res.string.search))
                        }

                        innerTextField()
                    }

                }
            },
            modifier = Modifier
        )
    }
}