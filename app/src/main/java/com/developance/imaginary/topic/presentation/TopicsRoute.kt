@file:OptIn(ExperimentalFoundationApi::class)

package com.developance.imaginary.topic.presentation

import android.content.res.Configuration
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.developance.imaginary.R
import com.developance.imaginary.common.ErrorSnackBar
import com.developance.imaginary.common.ImaginaryGradiantBackground
import com.developance.imaginary.photos.presentation.common.LoadingItem
import com.developance.imaginary.topic.domain.UserTopic
import com.developance.imaginary.topic.domain.previewUserUserTopics
import com.developance.imaginary.ui.theme.ImaginaryIcons
import com.developance.imaginary.ui.theme.largePadding
import com.developance.imaginary.ui.theme.smallPadding
import com.developance.imaginary.utils.NetworkImage
import com.developance.imaginary.utils.items

@Composable
fun TopicsRoute(
    modifier: Modifier = Modifier,
    onSave: () -> Unit,
) {
    val viewmodel: TopicsViewModel = hiltViewModel()
    val state = viewmodel.pager.collectAsLazyPagingItems()

    TopicsScreen(
        uiState = state,
        onSaveClick = onSave,
        onTopicClick = viewmodel::toggleFavorite,
        modifier = modifier
    )
}

@Composable
fun TopicsScreen(
    uiState: LazyPagingItems<UserTopic>,
    onSaveClick: () -> Unit,
    onTopicClick: (id: String, oldValue: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { com.developance.imaginary.common.DefaultAppBar() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onSaveClick,
                modifier = Modifier.navigationBarsPadding(),
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface
            ) {
                Icon(
                    painter = painterResource(id = ImaginaryIcons.NavigateNext),
                    contentDescription = stringResource(R.string.label_continue_to_courses)
                )
            }
        }
    ) { innerPadding ->
        ImaginaryGradiantBackground {

            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(innerPadding),
            ) {
                Text(
                    text = stringResource(R.string.choose_topics),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(
                        start = 36.dp,
                        top = 24.dp,
                        end = largePadding
                    )
                )
                Spacer(modifier = Modifier.weight(.5f))

                when (uiState.loadState.refresh) {
                    is LoadState.Loading -> LoadingItem(modifier = Modifier.fillMaxSize())
                    is LoadState.Error -> {
                        ErrorSnackBar(
                            message = "No data to show, we need connection!",
                            snackbarHostState = snackbarHostState,
                            withAction = true
                        ) {
                            uiState.retry()
                        }
                    }
                    is LoadState.NotLoading -> {


                        TopicsGrid(
                            uiState,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .heightIn(250.dp, 300.dp),
                            onTopicClick = onTopicClick
                        )

                    }
                }
                Spacer(Modifier.weight(.5f)) // center grid accounting for FAB
            }
        }
    }
}

@Composable
private fun TopicsGrid(
    topics: LazyPagingItems<UserTopic>,
    modifier: Modifier = Modifier,
    onTopicClick: (id: String, oldValue: Boolean) -> Unit
) {

    val staggeredGridCells =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridCells.Adaptive(minSize = 100.dp)
        } else StaggeredGridCells.Fixed(3)

    LazyHorizontalStaggeredGrid(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        rows = staggeredGridCells
    ) {
        items(topics) { topic ->
            TopicChip(topic = topic, onTopicClick)
        }
    }
}

@Composable
private fun TopicChip(topic: UserTopic, onTopicClick: (id: String, oldValue: Boolean) -> Unit) {
    val selected = topic.isFavorite
    val topicChipTransitionState = topicChipTransition(selected)

    Surface(
        modifier = Modifier
            .wrapContentHeight(),
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(
                topicChipTransitionState.cornerRadius
            )
        )
    ) {
        Row(
            modifier = Modifier.toggleable(
                value = selected,
                onValueChange = { onTopicClick(topic.id, selected) })
        ) {
            Box {
                NetworkImage(
                    url = topic.coverPhoto,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp), contentScale = ContentScale.Crop
                )
                if (topicChipTransitionState.selectedAlpha > 0f) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = .7f),
                        modifier = Modifier.matchParentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = topicChipTransitionState.selectedAlpha
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .scale(topicChipTransitionState.checkScale)
                        )
                    }
                }
            }
            Column(Modifier.padding(smallPadding)) {
                Text(
                    text = topic.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(
                        LocalContentColor provides LocalContentColor.current.copy(
                            alpha = .5f
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_grain_24),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(12.dp)
                        )
                        Text(
                            text = topic.totalPhotos.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Class holding animating values when transitioning topic chip states.
 */
private class TopicChipTransition(
    cornerRadius: State<Dp>,
    selectedAlpha: State<Float>,
    checkScale: State<Float>
) {
    val cornerRadius by cornerRadius
    val selectedAlpha by selectedAlpha
    val checkScale by checkScale
}

private enum class SelectionState { Unselected, Selected }

@Composable
private fun topicChipTransition(topicSelected: Boolean): TopicChipTransition {
    val transition = updateTransition(
        targetState = if (topicSelected) SelectionState.Selected else SelectionState.Unselected,
        label = ""
    )
    val cornerRadius = transition.animateDp(label = "") { state ->
        when (state) {
            SelectionState.Unselected -> 0.dp
            SelectionState.Selected -> 28.dp
        }
    }
    val selectedAlpha = transition.animateFloat(label = "") { state ->
        when (state) {
            SelectionState.Unselected -> 0f
            SelectionState.Selected -> 0.8f
        }
    }
    val checkScale = transition.animateFloat(label = "") { state ->
        when (state) {
            SelectionState.Unselected -> 0.6f
            SelectionState.Selected -> 1f
        }
    }
    return remember(transition) {
        TopicChipTransition(cornerRadius, selectedAlpha, checkScale)
    }
}

@Preview
@Composable
private fun TopicChipPreview() {
    TopicChip(topic = previewUserUserTopics()[0], onTopicClick = { _, _ -> })
}
