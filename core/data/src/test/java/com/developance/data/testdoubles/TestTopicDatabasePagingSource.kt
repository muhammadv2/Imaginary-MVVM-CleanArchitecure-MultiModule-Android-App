@file:OptIn(ExperimentalPagingApi::class)

package com.developance.data.testdoubles

import androidx.paging.*
import androidx.room.withTransaction
import coil.network.HttpException
import com.developance.database.TopicsDatabase
import com.developance.database.model.TopicEntity
import com.developance.model.asEntity
import com.developance.network.ImaginaryNetworkDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.IOException
import javax.inject.Inject

const val INITIAL_PAGE_KEY = 1
class TestTopicDatabasePagingSource @Inject constructor(
    private val topicsDatabase: TopicsDatabase,
    private val imaginaryApi: ImaginaryNetworkDataSource,
) : PagingSource<Int, TopicEntity>() {

    companion object{
         var entitiesStateFlow = MutableStateFlow(
            listOf(
                TopicEntity(
                    id = "bo8jQKTaE0Y",
                    slug = "wallpapers",
                    title = "Wallpapers",
                    description = "From epic drone shots to inspiring moments in nature — submit your best desktop and mobile backgrounds.\r\n\r\n",
                    totalPhotos = 11814,
                    coverPhoto = "https://plus.unsplash.com/premium_photo-1674657644569-787aa58cc804?ixlib=rb-4.0.3\u0026w=200\u0026fit=max\u0026q=80\u0026fm=jpg\u0026crop=entropy\u0026cs=tinysrgb",
                    isFavorite = false
                ),
                TopicEntity(
                    id = "KTaEbo8jQ0Y",
                    slug = "wallpapers",
                    title = "Wallpapers",
                    description = "From epic drone shots to inspiring moments in nature — submit your best desktop and mobile backgrounds.\r\n\r\n",
                    totalPhotos = 11814,
                    coverPhoto = "https://plus.unsplash.com/premium_photo-1674657644569-787aa58cc804?ixlib=rb-4.0.3\u0026w=200\u0026fit=max\u0026q=80\u0026fm=jpg\u0026crop=entropy\u0026cs=tinysrgb",
                    isFavorite = true
                ),
                TopicEntity(
                    id = "Q8jK0YTaboE",
                    slug = "wallpapers",
                    title = "Wallpapers",
                    description = "From epic drone shots to inspiring moments in nature — submit your best desktop and mobile backgrounds.\r\n\r\n",
                    totalPhotos = 11814,
                    coverPhoto = "https://plus.unsplash.com/premium_photo-1674657644569-787aa58cc804?ixlib=rb-4.0.3\u0026w=200\u0026fit=max\u0026q=80\u0026fm=jpg\u0026crop=entropy\u0026cs=tinysrgb",
                    isFavorite = false
                ),
            )
        )
    }
    override fun getRefreshKey(state: PagingState<Int, TopicEntity>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopicEntity> =
        LoadResult.Page(
            data = entitiesStateFlow.value,
            prevKey = null, //-> only paging forward
            nextKey = 1
        )

}

