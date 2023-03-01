@file:OptIn(ExperimentalFoundationApi::class)

package com.developance.imaginary.photos.presentation.search

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.developance.imaginary.common.ErrorSnackBar
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.photos.presentation.common.LoadingItem
import com.developance.imaginary.photos.presentation.common.PhotoItem
import com.developance.imaginary.ui.theme.ImaginaryIcons
import com.developance.imaginary.ui.theme.largePadding
import com.developance.imaginary.ui.theme.mediumPadding
import com.developance.imaginary.ui.theme.veryLargePadding
import com.developance.imaginary.utils.items
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchPhotoRoute(
    selectPhoto: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: SearchPhotosViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val textFieldState by viewModel.textFieldStateStream.collectAsStateWithLifecycle()
    val suggestionList by viewModel.suggestions.collectAsStateWithLifecycle()
    val searching by viewModel.searching.collectAsStateWithLifecycle()

    SearchPhotoScreen(
        state = state,
        textFieldState = textFieldState,
        suggestionList = suggestionList,
        searching = searching,
        selectPhoto = selectPhoto,
        searchImages = viewModel::searchImages,
        onTextFieldStateChange = viewModel::textFieldValue,
        snackbarHostState = snackbarHostState,
        modifier = modifier.padding(
            top = veryLargePadding,
            start = mediumPadding,
            end = mediumPadding
        )
    )
}

@Composable
fun SearchPhotoScreen(
    state: Flow<PagingData<UserPhoto>>,
    textFieldState: String,
    suggestionList: List<String>,
    searching: Boolean,
    searchImages: (String) -> Unit,
    onTextFieldStateChange: (String) -> Unit,
    selectPhoto: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    var emptyInput by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = textFieldState,
            onValueChange = { text ->
                if (text.trim().isBlank()) emptyInput = true
                else
                    if (text != textFieldState) {
                        emptyInput = false
                        onTextFieldStateChange(text)
                    }
            },
            supportingText = { if (emptyInput) Text(text = "Can't search null input") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = ImaginaryIcons.Search,
                    contentDescription = "search",
                    modifier = Modifier.clickable {
                        searchImages(textFieldState)
                    }
                )
            }
        )
        if (searching) {
            val uiState = state.collectAsLazyPagingItems()

            when (val loadState = uiState.loadState.refresh) {
                is LoadState.Loading -> LoadingItem(modifier = modifier.fillMaxSize())
                is LoadState.Error -> {
                    if (loadState.error.localizedMessage == INVALID_INPUT)
                        ErrorSnackBar(
                            message = INVALID_INPUT,
                            snackbarHostState = snackbarHostState
                        )
                    else
                        ErrorSnackBar(
                            message = CONNECTION_ERROR,
                            snackbarHostState = snackbarHostState,
                            withAction = true,
                            onActionPerform = { uiState.retry() }
                        )
                }
                is LoadState.NotLoading -> {
                    val cellConfiguration =
                        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            StaggeredGridCells.Adaptive(minSize = 175.dp)
                        } else StaggeredGridCells.Fixed(2)

                    LazyVerticalStaggeredGrid(
                        columns = cellConfiguration,
                        verticalArrangement = Arrangement.spacedBy(mediumPadding),
                        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                        contentPadding = PaddingValues(top = veryLargePadding),
                    ) {

                        items(uiState) { photo ->
                            PhotoItem(userPhoto = photo, selctedPhoto = { selectPhoto(it) })
                        }
                    }
                }
            }
        } else {
            EmptyScreen(Modifier.weight(1f), suggestionList, searchImages)
        }
    }
}

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    suggestionList: List<String>,
    searchImages: (String) -> Unit
) {
    LazyColumn(
        modifier.fillMaxHeight(.7f),
        contentPadding = PaddingValues(veryLargePadding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(largePadding),
    ) {
        items(suggestionList) { suggestion ->
            Text(
                text = suggestion,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    searchImages(suggestion)
                })
        }
    }
}

const val INVALID_INPUT = "Invalid input, please choose another word"
const val CONNECTION_ERROR = "No Connection, make sure to connect to the internet and try again"