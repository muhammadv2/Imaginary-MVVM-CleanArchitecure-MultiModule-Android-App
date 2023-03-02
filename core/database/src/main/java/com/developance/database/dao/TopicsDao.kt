package com.developance.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.developance.database.model.PartialTopicEntity
import com.developance.database.model.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicsDao {

    @Query("SELECT * FROM topics")
    fun getPagingSource(): PagingSource<Int, TopicEntity>

    @Query("SELECT * From topics WHERE is_favorite = 1")
    fun favoritedTopicsStream(): Flow<List<TopicEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreTopics(topics: List<TopicEntity>)

    @Update(entity = TopicEntity::class)
    suspend fun updateTopic(partialtopic: PartialTopicEntity)

}

