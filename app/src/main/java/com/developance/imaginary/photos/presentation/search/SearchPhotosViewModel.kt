@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.developance.imaginary.photos.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.developance.imaginary.data.local.trendingSearchkeywords
import com.developance.imaginary.photos.data.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchPhotosViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    private val _searching = MutableStateFlow(false)
    val searching = _searching.asStateFlow()

    private val _textFieldStateStream = MutableStateFlow("")
    val textFieldStateStream = _textFieldStateStream.asStateFlow()

    val suggestions = textFieldStateStream.mapLatest { state ->
        trendingSearchkeywords().filter { it.contains(state) }
    }.stateIn(
        viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5000L)
    )

    private var queryString: MutableStateFlow<String> = MutableStateFlow("")
    val uiState = queryString
        .filter { it.isNotBlank() }
        .flatMapLatest { slug ->
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    repository.searchPagingSource(slug)
                }).flow
        }.cachedIn(viewModelScope)

    fun textFieldValue(newvalue: String) {
        _textFieldStateStream.value = newvalue
    }

    fun searchImages(query: String) {
        _searching.value = true
        _textFieldStateStream.value = ""
        queryString.value = query
    }
}