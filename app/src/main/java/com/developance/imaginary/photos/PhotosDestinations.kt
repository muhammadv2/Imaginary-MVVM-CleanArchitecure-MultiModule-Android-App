package com.developance.imaginary.photos


import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.developance.imaginary.R
import com.developance.imaginary.photos.presentation.favorite.FavoritePhotosRoute
import com.developance.imaginary.photos.presentation.list.PhotosRoute
import com.developance.imaginary.photos.presentation.search.SearchPhotoRoute
import com.developance.imaginary.ui.theme.Icon
import com.developance.imaginary.ui.theme.ImaginaryIcons

fun NavGraphBuilder.photos(
    selectPhoto: (String, NavBackStackEntry) -> Unit,
    configureTopics: () -> Unit,
    finishActivity: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {

    composable(PhotoTaps.FEATURED_PHOTOS.route) { from ->
        BackHandler {
            finishActivity()
        }

        PhotosRoute(
            snackbarHostState = snackBarHostState,
            selectPhoto = { id -> selectPhoto(id, from) },
            configureTopics = configureTopics
        )
    }

    composable(route = PhotoDestinations.FAVORITE_PHOTOS_ROUTE) { from ->
        FavoritePhotosRoute(
            selectPhoto = { id -> selectPhoto(id, from) }
        )
    }

    composable(route = PhotoDestinations.SEARCH_PHOTOS_ROUTE) { from ->
        SearchPhotoRoute(snackbarHostState = snackBarHostState,
            selectPhoto = { id -> selectPhoto(id, from) })
    }
}

enum class PhotoTaps(
    @StringRes val title: Int,
    val selected: Icon,
    val unSelected: Icon,
    val route: String
) {
    SEARCH_PHOTOS(
        R.string.search,
        Icon.ImageVectorIcon(ImaginaryIcons.Search),
        Icon.ImageVectorIcon(ImaginaryIcons.Search),
        PhotoDestinations.SEARCH_PHOTOS_ROUTE
    ),
    FEATURED_PHOTOS(
        R.string.photos,
        Icon.DrawableResourceIcon(ImaginaryIcons.PhotoSelected),
        Icon.DrawableResourceIcon(ImaginaryIcons.PhotoUnSelected),
        PhotoDestinations.FEATURED_PHOTOS
    ),
    FAVORITE_PHOTOS(
        R.string.favorite,
        Icon.DrawableResourceIcon(ImaginaryIcons.FilledFavorite),
        Icon.DrawableResourceIcon(ImaginaryIcons.UnFilledFavorite),
        PhotoDestinations.FAVORITE_PHOTOS_ROUTE
    )
}

private object PhotoDestinations {
    const val FEATURED_PHOTOS = "featured_photos"
    const val FAVORITE_PHOTOS_ROUTE = "bookmarked_photos"
    const val SEARCH_PHOTOS_ROUTE = "search_photos"
}