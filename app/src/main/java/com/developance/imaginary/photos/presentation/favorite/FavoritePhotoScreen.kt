@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.developance.imaginary.photos.presentation.favorite

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.photos.presentation.common.LoadingItem
import com.developance.imaginary.photos.presentation.common.PhotoItem
import com.developance.imaginary.ui.theme.mediumPadding


@Composable
fun FavoritePhotosRoute(
    selectPhoto: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritePhotosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritePhotosScreen(uiState = uiState, selectPhoto = selectPhoto , modifier = modifier)
}

@Composable
fun FavoritePhotosScreen(
    uiState: List<UserPhoto>,
    selectPhoto: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cellConfiguration =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridCells.Adaptive(minSize = 175.dp)
        } else StaggeredGridCells.Fixed(2)

    if (uiState.isEmpty())
        LoadingItem(Modifier.fillMaxSize())

    LazyVerticalStaggeredGrid(
        columns = cellConfiguration,
        verticalArrangement = Arrangement.spacedBy(mediumPadding),
        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
        contentPadding = PaddingValues(mediumPadding),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(uiState) { photo ->
            PhotoItem(userPhoto = photo, selctedPhoto = selectPhoto)
        }
    }
}