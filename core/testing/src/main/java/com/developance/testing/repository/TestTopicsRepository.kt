
package com.developance.testing.repository

import com.developance.data.paging.TopicRemoteMediator
import com.developance.data.repository.TopicsRepository

class TestTopicsRepository : TopicsRepository {

    override fun getTopicsRemoteMediator(): TopicRemoteMediator {
        TODO("Not yet implemented")
    }

    override fun getTopicsPagingSource():
            androidx.paging.PagingSource<Int, com.developance.database.model.TopicEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavoriteTopic(id: String, value: Boolean) {
        TODO("Not yet implemented")
    }
}
