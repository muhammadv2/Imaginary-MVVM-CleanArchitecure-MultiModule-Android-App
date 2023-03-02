

package com.developance.testing.repository

import com.developance.data.paging.PhotosPagingSource
import com.developance.data.paging.SearchPagingSource
import com.developance.data.repository.PhotosRepository
import com.developance.model.data.Photo
import com.developance.model.data.Topic
import kotlinx.coroutines.flow.Flow

class TestPhotosRepository() : PhotosRepository {

    override val favoritePhotosIdsStream: Flow<Set<String>>
        get() = TODO("Not yet implemented")

    override val favoritedTopicsStream: Flow<List<Topic>>
        get() = TODO("Not yet implemented")


    override fun getPhotoPagingSource(topicsSlug: String): PhotosPagingSource {
        TODO("Not yet implemented")
    }

    override fun getSearchPagingSource(query: String): SearchPagingSource {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavoritePhoto(id: String, isBookmarked: Boolean) {
        TODO("Not yet implemented")
    }

    override fun fetchPhoto(id: String): Flow<Photo> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFavoritePhotos(ids: List<String>): List<Photo> {
        TODO("Not yet implemented")
    }


}
