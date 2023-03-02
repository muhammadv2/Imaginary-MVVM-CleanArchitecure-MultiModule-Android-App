
package com.developance.testing.repository

import com.developance.data.paging.TopicsPagingSource
import com.developance.data.repository.TopicsRepository

class TestTopicsRepository : TopicsRepository {

    override fun getTopicsRemoteMediator(): TopicsPagingSource {
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
