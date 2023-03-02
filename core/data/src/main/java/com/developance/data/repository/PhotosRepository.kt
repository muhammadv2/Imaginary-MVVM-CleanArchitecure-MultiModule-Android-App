package com.developance.data.repository

import com.developance.data.paging.PhotosPagingSource
import com.developance.data.paging.SearchPagingSource
import com.developance.model.data.Photo
import com.developance.model.data.Topic
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {

    val favoritePhotosIdsStream: Flow<Set<String>>

    val  favoritedTopicsStream :Flow<List<Topic>>
    fun getPhotoPagingSource(topicsSlug: String): PhotosPagingSource

    fun getSearchPagingSource(query: String): SearchPagingSource

    suspend fun toggleFavoritePhoto(id: String, isBookmarked: Boolean)

    fun fetchPhoto(id: String): Flow<Photo>

    suspend fun fetchFavoritePhotos(ids: List<String>): List<Photo>
}