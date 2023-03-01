package com.developance.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developance.database.dao.TopicsDao
import com.developance.database.model.TopicEntity


@Database(entities = [TopicEntity::class], version = 1)
abstract class TopicsDatabase :RoomDatabase(){
    abstract val topicDao : TopicsDao
}
