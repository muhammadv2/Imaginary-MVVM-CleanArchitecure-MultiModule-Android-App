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
    fun getFavoritedTopicsStream(): Flow<List<TopicEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAll(restaurants: List<TopicEntity>)

    @Update(entity = TopicEntity::class)
    suspend fun updateTopic(partialRestaurant: PartialTopicEntity)

    @Update(entity = TopicEntity::class)
    suspend fun updateAll(partialRestaurant: List<PartialTopicEntity>)

    @Query("SELECT * From topics WHERE is_favorite = 1")
    suspend fun getAllFavorited(): List<TopicEntity>

    @Query("DELETE FROM topics")
    suspend fun clearAll()
}

