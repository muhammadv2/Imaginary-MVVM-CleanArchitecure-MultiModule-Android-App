@file:OptIn(ExperimentalPagingApi::class)

package com.developance.data.paging

import androidx.paging.*
import androidx.room.withTransaction
import coil.network.HttpException
import com.developance.database.TopicsDatabase
import com.developance.database.model.TopicEntity
import com.developance.model.asEntity
import com.developance.network.ImaginaryNetworkDataSource
import java.io.IOException
import javax.inject.Inject


class TopicsPagingSource @Inject constructor(
    private val topicsDatabase: TopicsDatabase,
    private val imaginaryApi: ImaginaryNetworkDataSource,
) : RemoteMediator<Int, TopicEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopicEntity>
    ): MediatorResult {
        var pageKey = 1

        return try {
            val loadKey: Int? = when (loadType) {
                // REFRESH will always load the first page in the list.
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    pageKey++
                }
            }

            val response =
                imaginaryApi.fetchTopics(page = loadKey)

            topicsDatabase.withTransaction {
                topicsDatabase.topicDao.insertOrIgnoreTopics(response.map { it.asEntity() })
            }
            MediatorResult.Success(
                endOfPaginationReached = pageKey == 2 //todo unsplash topics is less than one pagex30
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

