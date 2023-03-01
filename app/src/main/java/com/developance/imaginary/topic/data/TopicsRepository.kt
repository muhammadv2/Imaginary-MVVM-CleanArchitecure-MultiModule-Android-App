package com.developance.imaginary.topic.data

import com.developance.imaginary.topic.data.local.PartialTopicEntity
import com.developance.imaginary.topic.data.local.RoomDatabase
import com.developance.imaginary.topic.data.local.TopicsLocalDataSource
import com.developance.imaginary.topic.data.remote.TopicsApiService
import com.developance.imaginary.topic.data.remote.TopicsPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicsRepository @Inject constructor(
    private val topicsLocalDataSource: TopicsLocalDataSource,
    private val topicsDb: RoomDatabase,
    private val topicsApiService: TopicsApiService
) {

    fun getTopicsRemoteMediator() =
        TopicsPagingSource(topicsApiService, topicsDb)
    fun getTopicsPagingSource() =
        topicsLocalDataSource.getPagingSource()
    suspend fun toggleFavoriteTopic(
        id: String,
        value: Boolean
    ) {
        topicsLocalDataSource.updateTopic(
            PartialTopicEntity(
                id = id,
                isFavorite = value
            )
        )
    }

}


