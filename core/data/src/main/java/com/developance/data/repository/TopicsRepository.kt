package com.developance.data.repository

import androidx.paging.PagingSource
import com.developance.data.paging.TopicsPagingSource
import com.developance.database.model.TopicEntity

interface TopicsRepository {

    fun getTopicsRemoteMediator(): TopicsPagingSource

    fun getTopicsPagingSource(): PagingSource<Int, TopicEntity>

    suspend fun toggleFavoriteTopic(id: String, value: Boolean)
}