package com.developance.data.repository

import androidx.paging.PagingSource
import com.developance.data.paging.TopicRemoteMediator
import com.developance.database.model.TopicEntity

interface TopicsRepository {

    fun getTopicsRemoteMediator(): TopicRemoteMediator

    fun getTopicsPagingSource(): PagingSource<Int, TopicEntity>

    suspend fun toggleFavoriteTopic(id: String, value: Boolean)
}