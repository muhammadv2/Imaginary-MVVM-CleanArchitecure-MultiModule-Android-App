@file:OptIn(ExperimentalPagingApi::class)

package com.developance.imaginary.topic.data.remote

import androidx.paging.*
import androidx.room.withTransaction
import coil.network.HttpException
import com.developance.imaginary.topic.data.local.TopicEntity
import com.developance.imaginary.topic.data.local.RoomDatabase
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


class TopicsPagingSource @Inject constructor(
    private val topicsApiService: TopicsApiService,
    private val roomDatabase: RoomDatabase
) : RemoteMediator<Int, TopicEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopicEntity>
    ): MediatorResult {
        var pageKey = 1

        return try {
            val loadKey: String? = when (loadType) {
                // REFRESH will always load the first page in the list.
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    pageKey += 1
                    pageKey.toString()
                }
            }

            val response =
                topicsApiService.fetchInterestingTopics(page = loadKey)

            roomDatabase.withTransaction {

                roomDatabase.topicDao.addAll(response.asEntities())
            }
            MediatorResult.Success(
                endOfPaginationReached = pageKey == 2
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

