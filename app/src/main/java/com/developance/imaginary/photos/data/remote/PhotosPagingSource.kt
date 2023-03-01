package com.developance.imaginary.photos.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.developance.imaginary.photos.domain.UserPhoto

class PhotosPagingSource(
    private val photoApiService: PhotosApiService,
    private val topicsSlug: String,
) :
    PagingSource<Int, UserPhoto>() {

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, UserPhoto>): Int {

        return (state.anchorPosition ?: 0) - state.config.initialLoadSize / 2.coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserPhoto> {
        val nextPageNumber = params.key ?: STARTING_KEY

        return try {
            val response: List<UserPhoto> =
                photoApiService.fetchTopicPhotos(
                    slug = topicsSlug,
                    page = nextPageNumber,
                ).asDomain()

            LoadResult.Page(
                data = response,
                prevKey = null, //-> only paging forward
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    companion object {
        const val STARTING_KEY = 1
    }
}

