package com.developance.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import coil.network.HttpException
import com.developance.model.data.UserPhoto
import com.developance.network.ImaginaryNetworkDataSource
import com.developance.network.model.asDomain
import java.io.IOException

class PhotosPagingSource(
    private val photoApiService: ImaginaryNetworkDataSource,
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
                ).map { it.asDomain() }

            LoadResult.Page(
                data = response,
                prevKey = null, //-> only paging forward
                nextKey = nextPageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    companion object {
        const val STARTING_KEY = 1
    }
}

