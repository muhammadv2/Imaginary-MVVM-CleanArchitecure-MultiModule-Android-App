package com.developance.imaginary.photos.data

import com.developance.imaginary.data.di.IoDispatcher
import com.developance.imaginary.photos.data.local.PhotosStoreManager
import com.developance.imaginary.photos.data.remote.PhotosApiService
import com.developance.imaginary.photos.data.remote.PhotosPagingSource
import com.developance.imaginary.photos.data.remote.SearchPagingSource
import com.developance.imaginary.photos.data.remote.asDomain
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.topic.data.local.TopicsLocalDataSource
import com.developance.imaginary.topic.data.local.asExternalModel
import com.developance.imaginary.topic.domain.UserTopic
import com.developance.imaginary.topic.domain.defaultRandomTopic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val photosApiService: PhotosApiService,
    private val dataStoreManager: PhotosStoreManager,
    private val topicsLocalDataSource: TopicsLocalDataSource,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    val favoritePhotosIdsStream = dataStoreManager.bookmarkedPhotosIds

    fun photoPagingSource(topicsSlug: String) =
        PhotosPagingSource(photosApiService, topicsSlug)

    fun searchPagingSource(query: String) =
        SearchPagingSource(photosApiService, query)

    suspend fun toggleFavoritePhoto(id: String, isBookmarked: Boolean) {

        if (isBookmarked)
            dataStoreManager.addBookmarkedPhotoId(id)
        else
            dataStoreManager.removeBookmarkedPhotoId(id)
    }

    fun getPhoto(id: String): Flow<UserPhoto> = flow {
        emit(photosApiService.fetchPhoto(id).asDomain())
    }

    suspend fun getFavoritePhotos(ids: List<String>) = withContext(defaultDispatcher) {
        val userPhotoDetails = mutableListOf<UserPhoto>()
        ids.forEach {
            launch {
                try {
                    userPhotoDetails.add(photosApiService.fetchPhoto(it).asDomain())
                } catch (ex: Exception) {
                    //no need to handle exception as if any happen data will not be added to the list
                    Timber.d("Exception ${ex.message}")
                }
            }
        }
        userPhotoDetails
    }

    fun getFavoriteTopics(): Flow<List<UserTopic>?> = flow {
        val favoriteTopics = topicsLocalDataSource.getFavoritedTopics().asExternalModel()
        if (favoriteTopics.isNotEmpty())
            emit(topicsLocalDataSource.getFavoritedTopics().asExternalModel())
        else
            emit(listOf(defaultRandomTopic))
    }

}

