package com.developance.imaginary.photos.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.developance.imaginary.photos.data.remote.PhotosPagingSource.Companion.STARTING_KEY
import com.developance.imaginary.photos.domain.UserPhoto
import com.developance.imaginary.photos.presentation.search.INVALID_INPUT
import java.io.IOException


class SearchPagingSource(
    private val photoApiService: PhotosApiService,
    private val searchText: String,
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
                photoApiService.searchPhotos(
                    query = searchText,
                    page = nextPageNumber,
                ).result.asDomain()
            if (response.isEmpty())
                return LoadResult.Error(IllegalStateException(INVALID_INPUT))
            LoadResult.Page(
                data = response,
                prevKey = null, //-> only paging forward
                nextKey = nextPageNumber + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }

    }

    companion object {
        const val STARTING_KEY = 1
    }
}

