package com.developance.imaginary.photos.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developance.imaginary.photos.data.PhotosRepository
import com.developance.imaginary.photos.domain.UserPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<List<UserPhoto>>(emptyList())
    val uiState: StateFlow<List<UserPhoto>> = _uiState

    init {
        viewModelScope.launch {
            repository.favoritePhotosIdsStream.collectLatest { ids ->
                val photos = repository.getFavoritePhotos(ids.toList())
                _uiState.value = photos
            }
        }
    }
}


