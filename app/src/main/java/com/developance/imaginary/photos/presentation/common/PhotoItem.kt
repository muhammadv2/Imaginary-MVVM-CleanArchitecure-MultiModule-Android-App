package com.developance.imaginary.photos.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.utils.BlurNetworkImage


@Composable
fun PhotoItem(
    userPhoto: UserPhoto, selctedPhoto: (String) -> Unit, modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clickable(
                onClick = { selctedPhoto(userPhoto.id) }
            ), shape = RoundedCornerShape(8.dp)) {
        BlurNetworkImage(
            url = userPhoto.urls.small,
            userPhoto = userPhoto,
            contentDescription = userPhoto.description,
        )
    }
}
