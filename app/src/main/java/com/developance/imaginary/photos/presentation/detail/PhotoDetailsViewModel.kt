package com.developance.imaginary.photos.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developance.imaginary.MainDestinations
import com.developance.imaginary.photos.data.PhotosRepository
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.data.di.IoDispatcher
import com.developance.imaginary.data.remote.AndroidDownloader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PhotosRepository,
    private val downloader: AndroidDownloader,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val photoId = savedStateHandle.get<String>(MainDestinations.PHOTO_ID) ?: "11"

    val uiState: StateFlow<PhotoDetailsUiState> =
        repository.favoritePhotosIdsStream.combine(repository.getPhoto(photoId))
        { favList, photo ->
            PhotoDetailsUiState.Success(SavablePhoto(photo, favList.contains(photo.id)))
        }.catch {
            PhotoDetailsUiState.Error(it.message ?: "Connection either slow or lost :/")
        }.stateIn(
            scope = viewModelScope,
            initialValue = PhotoDetailsUiState.Loading,
            started = SharingStarted.WhileSubscribed(5000L)
        )

    fun toggleFavorite(id: String, isBookmarked: Boolean) {
        viewModelScope.launch(ioDispatcher)
        { repository.toggleFavoritePhoto(id, isBookmarked) }
    }

    fun downloadPhoto(userPhoto: UserPhoto) {
        downloader.downloadFile(userPhoto)
    }
}

sealed interface PhotoDetailsUiState {
    data class Success(val savablePhoto: SavablePhoto) : PhotoDetailsUiState
    object Loading : PhotoDetailsUiState

    data class Error(val message: String) : PhotoDetailsUiState
}

data class SavablePhoto(
    val userPhoto: UserPhoto,
    val isBookmarked: Boolean = false,
)
