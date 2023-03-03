package com.developance.data.testdoubles

import androidx.paging.PagingSource
import com.developance.database.dao.TopicsDao
import com.developance.database.model.PartialTopicEntity
import com.developance.database.model.TopicEntity
import kotlinx.coroutines.flow.Flow

class TestTopicDao : TopicsDao {

    override fun getPagingSource(): PagingSource<Int, TopicEntity> {
        TODO()
    }

    override fun favoritedTopicsStream(): Flow<List<TopicEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrIgnoreTopics(topics: List<TopicEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTopic(partialtopic: PartialTopicEntity) {
        TODO("Not yet implemented")
    }
}