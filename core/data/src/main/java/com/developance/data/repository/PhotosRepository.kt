package com.developance.data.repository

import com.developance.data.paging.PhotosPagingSource
import com.developance.data.paging.SearchPagingSource
import com.developance.model.data.UserPhoto
import com.developance.model.data.UserTopic
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    val favoritePhotosIdsStream: Flow<Set<String>>

    fun getPhotoPagingSource(topicsSlug: String): PhotosPagingSource

    fun getSearchPagingSource(query: String): SearchPagingSource

    suspend fun toggleFavoritePhoto(id: String, isBookmarked: Boolean)

    fun fetchPhoto(id: String): Flow<UserPhoto>

    suspend fun fetchFavoritePhotos(ids: List<String>): List<UserPhoto>

    fun fetchFavoriteTopics(): Flow<List<UserTopic>?>

}