@file:OptIn(ExperimentalMaterial3Api::class)

package com.developance.imaginary.photos.presentation.detail

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.developance.imaginary.common.ErrorSnackBar
import com.developance.imaginary.common.IconText
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.photos.domain.previewPhotos
import com.developance.imaginary.photos.presentation.common.LoadingItem
import com.developance.imaginary.ui.theme.*
import com.developance.imaginary.utils.*
import kotlinx.coroutines.launch

@Composable
fun PhotoDetailsRoute(
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    when (val uiState = state) {
        is PhotoDetailsUiState.Loading -> {
            LoadingItem()
        }
        is PhotoDetailsUiState.Error -> {
            ErrorSnackBar(message = uiState.message, snackbarHostState = snackBarHostState)
        }
        is PhotoDetailsUiState.Success -> {
            PhotoDetailsScreen(
                savablePhoto = uiState.savablePhoto,
                toggleBookmark = viewModel::toggleFavorite,
                downloadImage = { photo ->
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Download Started..",
                            duration = SnackbarDuration.Short,
                        )
                    }
                    viewModel.downloadPhoto(photo)
                },
                modifier = modifier
            )
        }
    }
}


@Composable
private fun PhotoDetailsScreen(
    savablePhoto: SavablePhoto,
    toggleBookmark: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    downloadImage: (UserPhoto) -> Unit,
) {
    val blurHashPainter = blurHashFromPhoto(savablePhoto.userPhoto)

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberSheetState(skipHalfExpanded = true)

    val showInfoBottomSheet = {
        scope.launch {
            openBottomSheet = true
        }
    }
    BackHandler(enabled = openBottomSheet) {
        openBottomSheet = false
    }

    BoxWithConstraints(
        modifier =
        modifier
            .paint(blurHashPainter)
            .fillMaxSize()
    ) {

        if (maxWidth < 500.dp)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                BoxWithConstraints(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(
                            top = mediumPadding,
                        )
                        .weight(1f)
                        .clipToBounds()

                ) {
                    BlurMaxSizeNetworkImage(
                        userPhoto = savablePhoto.userPhoto,
                        boxWithConstraintSize = BoxWithConstraintSize(
                            height = maxHeight,
                            width = maxWidth
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(top = veryLargePadding)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                ) {
                    ActionBarContent(
                        savablePhoto,
                        toggleBookmark,
                        downloadImage,
                        showInfoDialog = { showInfoBottomSheet() })
                }
            }
        else
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .clipToBounds(),
                    contentAlignment = Alignment.Center
                ) {
                    BlurMaxSizeNetworkImage(
                        userPhoto = savablePhoto.userPhoto,
                        boxWithConstraintSize = BoxWithConstraintSize(
                            height = maxHeight,
                            width = maxWidth
                        )
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight()
                        .background(Color.Transparent)
                ) {
                    ActionBarContent(
                        savablePhoto,
                        toggleBookmark,
                        downloadImage,
                        showInfoDialog = { showInfoBottomSheet() })
                }
            }
    }
    if (openBottomSheet)
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
//            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            PhotoInfo(userPhoto = savablePhoto.userPhoto)
        }
}


@Composable
fun ActionBarContent(
    savablePhoto: SavablePhoto,
    toggleBookmark: (String, Boolean) -> Unit,
    downloadImage: (UserPhoto) -> Unit,
    showInfoDialog: () -> Unit,
) {
    ActionIcon(iconRes = ImaginaryIcons.Download) {
        downloadImage(savablePhoto.userPhoto)
    }
    val icon =
        if (savablePhoto.isBookmarked)
            ImaginaryIcons.FilledFavorite
        else
            ImaginaryIcons.UnFilledFavorite
    ActionIcon(icon) {
        toggleBookmark(savablePhoto.userPhoto.id, !savablePhoto.isBookmarked)
    }

    ActionIcon(iconRes = ImaginaryIcons.Info) {
        showInfoDialog()
    }

}

@Composable
fun ActionIcon(
    @DrawableRes iconRes: Int,
    action: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .clip(FloatingActionButtonDefaults.shape)
            .size(48.dp)
            .clickable { action() },
        color = Color(0x42000000), contentColor = Color.White
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "",
            modifier = Modifier.padding(mediumPadding)
        )
    }
}

@Composable
fun PhotoInfo(userPhoto: UserPhoto, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(mediumPadding),
        modifier = modifier
            .fillMaxWidth()
            .padding(veryLargePadding)

    ) {
        PhotoHotLinkingDetails(
            userPhoto = userPhoto,
            modifier = Modifier
                .padding(bottom = veryLargePadding)
        )
        Row(
            modifier = Modifier.padding(smallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(largePadding)
        ) {
            IconText(iconId = ImaginaryIcons.Likes, text = userPhoto.likes.toString())
            Divider(
                Modifier
                    .height(12.dp)
                    .width(1.dp)
            )
            IconText(iconId = ImaginaryIcons.Download, text = userPhoto.downloads.toString())
        }
        IconText(
            iconId = ImaginaryIcons.Location,
            text = userPhoto.location?.asString()
        )
        IconText(iconId = ImaginaryIcons.Calendar, text = userPhoto.createdAt)
        IconText(
            iconId = ImaginaryIcons.Size,
            text = "Width ${userPhoto.width} , Height ${userPhoto.height}"
        )
    }
}

@Composable
fun PhotoHotLinkingDetails(userPhoto: UserPhoto, modifier: Modifier = Modifier) {
    val userUrl: (username: String) -> String = { username ->
        "https://unsplash.com/@$username?utm_source=your_app_name&utm_medium=referral"
    }

    val unsplashUrl = "https://unsplash.com/?utm_source=imaginary&utm_medium=referral"

    val userText = buildStringFromTextAndLink(
        prefix = "Photo by ",
        name = userPhoto.user.name,
        url = userUrl(userPhoto.user.userName),
    )
    val unsplashText = buildStringFromTextAndLink(
        prefix = " on ",
        name = "Unsplash",
        url = unsplashUrl
    )
    Row(modifier) {
        PhotoLinkingClickableText(text = userText)
        PhotoLinkingClickableText(text = unsplashText)

    }
}

// 🔥 Clickable text returns position of text that is clicked in onClick callback
@Composable
fun PhotoLinkingClickableText(modifier: Modifier = Modifier, text: AnnotatedString) {
    val uriHandler = LocalUriHandler.current
    ClickableText(
        modifier = modifier,
        text = text.capitalize(),
        onClick = {
            text
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        },
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
    )
}

@Preview(backgroundColor = 0xFF001F28)
@Composable
fun PhotoInfoPrev() {
    Box(Modifier.fillMaxSize()) {
        PhotoInfo(userPhoto = previewPhotos()[0])
    }
}