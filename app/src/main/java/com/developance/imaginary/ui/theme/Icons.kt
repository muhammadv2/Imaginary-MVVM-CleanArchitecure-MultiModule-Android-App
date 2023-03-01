package com.developance.imaginary.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.developance.imaginary.R

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}

object ImaginaryIcons {
    const val Calendar = R.drawable.ic_calenedar_20
    const val Location = R.drawable.ic_location_20
    const val Size = R.drawable.ic_size_20
    const val NavigateNext = R.drawable.navigate_next_48
    const val PhotoSelected = R.drawable.photos_selected_24
    const val PhotoUnSelected = R.drawable.photos_unselected_24
    const val Info = R.drawable.ic_info_24
    const val Download = R.drawable.ic_download_24
    const val UnFilledFavorite = R.drawable.ic_star_24
    const val FilledFavorite = R.drawable.filled_star_24
    const val Edit = R.drawable.edit_square_48
    const val Likes = R.drawable.thumb_up_20
    const val DownloadSmall = R.drawable.ic_download_20
    val Search = Icons.Rounded.Search
    val Settings = Icons.Rounded.Settings
}