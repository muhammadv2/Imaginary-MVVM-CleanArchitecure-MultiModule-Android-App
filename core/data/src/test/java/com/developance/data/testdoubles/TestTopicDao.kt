package com.developance.data.testdoubles

import androidx.paging.PagingSource
import com.developance.database.dao.TopicsDao
import com.developance.database.model.PartialTopicEntity
import com.developance.database.model.TopicEntity
import com.developance.model.data.CoverPhoto
import com.developance.model.data.ImageUrls
import com.developance.network.model.NetworkTopic
import kotlinx.coroutines.flow.*

/**
 * Test double for [TopicsDao]
 */
class TestTopicDao : TopicsDao {
    private val testTopicDatabasePagingSource =
        TestTopicDatabasePagingSource

    override fun getPagingSource(): PagingSource<Int, TopicEntity> {
        throw NotImplementedError("paging has another test")
    }


    override fun favoritedTopicsStream(): Flow<List<TopicEntity>> =
        testTopicDatabasePagingSource.entitiesStateFlow.map { topics ->
            topics.filter { topic ->
                topic.isFavorite
            }
        }


    override suspend fun insertOrIgnoreTopics(topics: List<TopicEntity>) {
        testTopicDatabasePagingSource.entitiesStateFlow.value = topics
    }

    override suspend fun updateTopic(partialtopic: PartialTopicEntity) {
        throw NotImplementedError("Unused in tests")
    }
}