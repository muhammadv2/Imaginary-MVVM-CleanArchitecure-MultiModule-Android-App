/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.developance.imaginary.utils

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.developance.imaginary.photos.domain.UserPhoto
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * A wrapper around [AsyncImage], setting a default [contentScale] and showing
 * content while loading.
 */
@Composable
fun BlurNetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    userPhoto: UserPhoto,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
) {

    val height = calculateHeightFromWidthDownSizing(userPhoto = userPhoto)

    val blurHashPainter = BlurHashPainter(
        blurHash = userPhoto.blurHash,
        width = userPhoto.width.toInt(),
        height = userPhoto.height.toInt(),
        punch = 1F,
        scale = 0.1F
    )
    AsyncImage(
        contentDescription = contentDescription,
        modifier = modifier.height(height.dp),
        model = ImageRequest.Builder(
            LocalContext.current
        ).data(url).crossfade(true).build(),
        error = blurHashPainter,
        placeholder = blurHashPainter,
        contentScale = contentScale,
    )
}

@Composable
fun calculateHeightFromWidthDownSizing(userPhoto: UserPhoto): Int {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenDensity = configuration.densityDpi / 160f
    val availableColumns = screenWidthDp / 170 //170 fixed size for column width
    val columnWidthPx = screenDensity * screenWidthDp / availableColumns
    val photoWidthAspectRatio = userPhoto.width.toInt() / columnWidthPx
    return (userPhoto.height / photoWidthAspectRatio / screenDensity).toInt() // height in dp
}

@Composable
fun BlurMaxSizeNetworkImage(
    modifier: Modifier = Modifier,
    userPhoto: UserPhoto,
    boxWithConstraintSize: BoxWithConstraintSize,
    contentScale: ContentScale = ContentScale.Crop,
) {

    val imageSize = calculateDimenstionsFromAvailableSize(
        containerSize = boxWithConstraintSize,
        photoWidth = userPhoto.width.toInt(),
        photoHeight = userPhoto.height.toInt()
    )
    val blurHashPainter = blurHashFromPhoto(userPhoto)

    Timber.d("container height ${boxWithConstraintSize.height} width ${boxWithConstraintSize.width}")
    Timber.d("image height ${imageSize.height} width ${imageSize.width}")
    AsyncImage(
        modifier = modifier
            .height(imageSize.height.dp)
            .width(imageSize.width.dp),
        model = ImageRequest.Builder(
            LocalContext.current
        ).data(userPhoto.urls.regular).crossfade(true).build(),
        error = blurHashPainter,
        placeholder = blurHashPainter,
        contentScale = contentScale,
        contentDescription = userPhoto.description,
    )
}

/**
 * Based on the screen size and orientation and the original size of the image to draw
 * If the orientation is portrait then we have limited width, so we calculate height based on
 * the ratio of downsizing the width to fit the screen
 * else the orientation is in landscape the heigth here will be the limited factor so calculate
 * based on the ratio of downsizing itself by dividing the heigth of image on the heigth of screen
 * and the same with width but reversed
 *
 * so when width is limited we calculate the heigth based on the available width
 * and height is limited we calculate the height based on dividing itself on the screen max height to get heigth that fits the screen
 */
@Composable
fun calculateDimenstionsFromAvailableSize(
    containerSize: BoxWithConstraintSize, // if there's any padding in the original view to be calculated
    photoWidth: Int,
    photoHeight: Int
): PhotoSize {
    val orientation = LocalConfiguration.current.orientation // 1-portrait // 2-landscape
    val widthRatio = photoWidth / containerSize.width.dpToPx()
    val heightRatio = photoHeight / containerSize.height.dpToPx()
    val height =
        if (orientation == 1) photoHeight.pxToDp() / widthRatio
        else photoHeight.pxToDp() / heightRatio //this added
    val width =
        if (orientation == 1) photoWidth.pxToDp() / widthRatio //this also
        else photoWidth.pxToDp() / heightRatio

    return PhotoSize(height = height.roundToInt(), width = width.roundToInt())
}


@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        contentDescription = contentDescription,
        modifier = modifier,
        model = ImageRequest.Builder(
            LocalContext.current
        ).data(url).crossfade(true).build(),
        contentScale = contentScale,
    )
}

data class BoxWithConstraintSize(val height: Dp, val width: Dp)

@Composable
fun Dp.dpToPx() = this.value * (LocalConfiguration.current.densityDpi / 160f)

@Composable
fun Int.pxToDp() = this / (LocalConfiguration.current.densityDpi / 160f)
data class PhotoSize(val height: Int, val width: Int)