package com.developance.database

import com.developance.database.dao.TopicsDao
import com.developance.database.model.PartialTopicEntity
import com.developance.database.model.TopicEntity
import javax.inject.Inject

class TopicsLocalDataSource @Inject constructor(private val dao: TopicsDao) {
    suspend fun addAll(topics: List<TopicEntity>) {
        dao.addAll(topics)
    }

    fun getPagingSource() = dao.getPagingSource()

    suspend fun updateTopic(partialTopic: PartialTopicEntity) {
        dao.updateTopic(partialTopic)
    }


    suspend fun getFavoritedTopics(): List<TopicEntity> =
        dao.getAllFavorited()
}