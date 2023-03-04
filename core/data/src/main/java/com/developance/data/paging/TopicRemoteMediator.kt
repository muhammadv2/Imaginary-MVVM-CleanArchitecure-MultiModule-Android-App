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

const val INITIAL_PAGE_KEY = 1
class TopicRemoteMediator @Inject constructor(
    private val topicsDatabase: TopicsDatabase,
    private val imaginaryApi: ImaginaryNetworkDataSource,
) : RemoteMediator<Int, TopicEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopicEntity>
    ): MediatorResult {

        return try {
            val loadKey: Int = when (loadType) {
                // REFRESH will always load the first page in the list.
                LoadType.REFRESH -> INITIAL_PAGE_KEY
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.pages.size + 1
                }
            }

            val response = imaginaryApi.fetchTopics(page = loadKey)

            val endOfPaginationReached = response.isEmpty()
            topicsDatabase.withTransaction {
                topicsDatabase.topicDao.insertOrIgnoreTopics(response.map { it.asEntity() })
            }
            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

