@file:OptIn(ExperimentalPagingApi::class)

package com.developance.imaginary.topic.presentation


import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.developance.asResult
import com.developance.imaginary.topic.data.TopicsRepository
import com.developance.imaginary.data.di.IoDispatcher
import com.developance.imaginary.topic.data.local.asExternalModel
import com.developance.imaginary.topic.domain.ToggleTopicUseCase
import com.developance.imaginary.topic.domain.UserTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TopicsViewModel @Inject constructor(
    private val topicsRepository: TopicsRepository,
    private val toggleTopicUseCase: ToggleTopicUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val pager = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = topicsRepository.getTopicsRemoteMediator(),
    ) {
        topicsRepository.getTopicsPagingSource()
    }.flow
        .map { pagingData -> pagingData.map { it.asExternalModel() } }
        .cachedIn(viewModelScope)


    fun toggleFavorite(id: String, oldValue: Boolean) {
        viewModelScope.launch(ioDispatcher)
        { toggleTopicUseCase(id, oldValue) }
    }

}
