package com.developance.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.developance.model.data.Photo
import com.developance.network.ImaginaryNetworkDataSource
import com.developance.network.model.asExternalModel
import java.io.IOException

class PhotosPagingSource(
    private val photoApiService: ImaginaryNetworkDataSource,
    private val topicsSlug: String,
) :
    PagingSource<Int, Photo>() {

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val nextPageNumber = params.key ?: STARTING_KEY

        return try {
            val response: List<Photo> =
                photoApiService.fetchTopicPhotos(
                    slug = topicsSlug,
                    page = nextPageNumber,
                ).map { it.asExternalModel() }

            LoadResult.Page(
                data = response,
                prevKey = null, //-> only paging forward
                nextKey = nextPageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
        // TODO Must check for HttpException for any non-2xx HTTP status codes.

    }

    companion object {
        const val STARTING_KEY = 1
    }
}

