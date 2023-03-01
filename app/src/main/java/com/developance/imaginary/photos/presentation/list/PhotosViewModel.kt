package com.developance.imaginary.photos.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.developance.imaginary.photos.data.PhotosRepository
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.topic.domain.UserTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repository: PhotosRepository,
) : ViewModel() {

    private val topicSelectedFlow = MutableStateFlow("")

    val uiState: Flow<PagingData<UserPhoto>> = topicSelectedFlow.flatMapLatest { slug ->
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                repository.photoPagingSource(slug)
            }).flow
    }.cachedIn(viewModelScope)

    val favoriteTopics = repository.getFavoriteTopics()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun selectedTopic(slug: String) {
        topicSelectedFlow.value = slug
    }
}

