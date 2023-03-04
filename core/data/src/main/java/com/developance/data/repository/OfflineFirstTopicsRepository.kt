package com.developance.data.repository

import com.developance.data.paging.TopicRemoteMediator
import com.developance.database.TopicsDatabase
import com.developance.database.model.PartialTopicEntity
import com.developance.network.ImaginaryNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstTopicsRepository @Inject constructor(
    private val topicsDb: TopicsDatabase,
    private val imaginaryApi: ImaginaryNetworkDataSource
) : TopicsRepository {

    override fun getTopicsRemoteMediator() =
        TopicRemoteMediator(topicsDb, imaginaryApi)

    override fun getTopicsPagingSource() =
        topicsDb.topicDao.getPagingSource()

    override suspend fun toggleFavoriteTopic(
        id: String,
        value: Boolean
    ) {
        topicsDb.topicDao.updateTopic(
            PartialTopicEntity(
                id = id,
                isFavorite = value
            )
        )
    }

}


