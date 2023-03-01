package com.developance.imaginary.topic.data.local

import javax.inject.Inject

class TopicsLocalDataSource @Inject constructor(private val dao: TopicsDao) {

//    fun getTopics() : Flow<List<TopicEntity>> = dao.getTopics()

    suspend fun addAll(topics: List<TopicEntity>) {
        dao.addAll(topics)
    }

    fun getPagingSource() = dao.pagingSource()

    suspend fun updateTopic(partialTopic: PartialTopicEntity) {
        dao.updateTopic(partialTopic)
    }


    suspend fun getFavoritedTopics(): List<TopicEntity> =
        dao.getAllFavorited()
}