@file:OptIn(ExperimentalFoundationApi::class)

package com.developance.imaginary.photos.presentation.list

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.developance.imaginary.R
import com.developance.imaginary.common.ErrorSnackBar
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.photos.presentation.common.LoadingItem
import com.developance.imaginary.photos.presentation.common.PhotoItem
import com.developance.imaginary.topic.domain.UserTopic
import com.developance.imaginary.topic.domain.defaultRandomTopic
import com.developance.imaginary.topic.domain.previewUserUserTopics
import com.developance.imaginary.ui.theme.*
import com.developance.imaginary.utils.items

@Composable
fun PhotosRoute(
    snackbarHostState: SnackbarHostState,
    selectPhoto: (String) -> Unit,
    configureTopics: () -> Unit
) {

    val viewmodel: PhotosViewModel = hiltViewModel()
    val favoriteTopics by viewmodel.favoriteTopics.collectAsStateWithLifecycle()
    val uiState = viewmodel.uiState.collectAsLazyPagingItems()

    PhotoScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        favoriteTopics = favoriteTopics,
        selectTopic = viewmodel::selectedTopic,
        selectPhoto = selectPhoto,
        configureTopics = configureTopics
    )

}

@Composable
fun PhotoScreen(
    snackbarHostState: SnackbarHostState,
    uiState: LazyPagingItems<UserPhoto>,
    favoriteTopics: List<UserTopic>?,
    selectTopic: (String) -> Unit,
    selectPhoto: (String) -> Unit,
    configureTopics: () -> Unit
) {
    val refreshLoadState = uiState.loadState.refresh

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopicsRow(
            favoriteTopics = favoriteTopics,
            selectTopic = selectTopic,
            configureTopics = configureTopics,
        )
        when (refreshLoadState) {
            is LoadState.Loading -> LoadingItem(modifier = Modifier.fillMaxSize())
            is LoadState.Error -> {
                ErrorSnackBar(
                    message = "No data to show, we need connection!",
                    snackbarHostState = snackbarHostState,
                    withAction = true,
                    onActionPerform = { uiState.retry() }
                )
            }
            is LoadState.NotLoading -> {
                PhotosStaggeredGrid(
                    Modifier.fillMaxSize(),
                    uiState,
                    selectPhoto
                )
            }
        }
    }
}


@Composable
fun PhotosStaggeredGrid(
    modifier: Modifier = Modifier,
    uiState: LazyPagingItems<UserPhoto>,
    selectPhoto: (String) -> Unit
) {
    val cellConfiguration =
        if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
            StaggeredGridCells.Adaptive(minSize = 175.dp)
        } else StaggeredGridCells.Fixed(2)

    LazyVerticalStaggeredGrid(
        columns = cellConfiguration,
        verticalArrangement = Arrangement.spacedBy(mediumPadding),
        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
        contentPadding = PaddingValues(mediumPadding),
        modifier = modifier
    ) {

        items(uiState) { photo ->
            PhotoItem(userPhoto = photo, selctedPhoto = selectPhoto)
        }
    }
}

@Composable
fun TopicsRow(
    favoriteTopics: List<UserTopic>?,
    modifier: Modifier = Modifier,
    selectTopic: (String) -> Unit,
    configureTopics: () -> Unit
) {
    var state by rememberSaveable { mutableStateOf(0) }
    val titles = favoriteTopics?.map { it.title }

    if (titles != null && titles.isNotEmpty())
        Row(modifier) {
            LaunchedEffect(key1 = state) {
                selectTopic(favoriteTopics[state].slug)
            }
            ScrollableTabRow(selectedTabIndex = state) {
                titles.forEachIndexed { index, title ->
                    Tab(selected = state == index,
                        onClick = {
                            state = index
                            selectTopic(favoriteTopics[index].slug)
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    )
                }
                EditIcon(Modifier.size(24.dp), configureTopics = configureTopics)
            }
        }
}


@Composable
fun EditIcon(modifier: Modifier = Modifier, configureTopics: () -> Unit) {
    Icon(
        painter = painterResource(id = ImaginaryIcons.Edit),
        contentDescription = stringResource(R.string.edit_topics),
        modifier = modifier
            .padding(largePadding)
            .clickable {
                configureTopics()
            }
    )
}

@Preview(widthDp = 400, backgroundColor = 0xFFFFD6FA)
@Composable
fun HeadPreview() {
    TopicsRow(favoriteTopics = listOf(defaultRandomTopic), selectTopic = {}, configureTopics = {})
}
