package com.developance.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.developance.model.data.Photo
import com.developance.network.ImaginaryNetworkDataSource
import com.developance.network.model.asExternalModel
import java.io.IOException


class SearchPagingSource(
    private val photoApiService: ImaginaryNetworkDataSource,
    private val searchText: String,
) :
    PagingSource<Int, Photo>() {

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int {

        return (state.anchorPosition ?: 0) - state.config.initialLoadSize / 2.coerceAtLeast(0)

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val nextPageNumber = params.key ?: STARTING_KEY
        return try {
            val response: List<Photo> =
                photoApiService.searchPhotos(
                    query = searchText,
                    page = nextPageNumber,
                ).result.map { it.asExternalModel() }
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

const val INVALID_INPUT = "Invalid input, please choose another word"
const val CONNECTION_ERROR = "No Connection, make sure to connect to the internet and try again"