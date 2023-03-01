package com.developance.data.repository

import com.developance.model.data.UserPhoto
import com.developance.model.data.UserTopic
import com.developance.model.data.defaultRandomTopic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface PhotosRepository {

    val bookmarkedPhotosIds: Flow<Set<String>>

    fun photoPagingSource(topicsSlug: String): PhotosPagingSource

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

