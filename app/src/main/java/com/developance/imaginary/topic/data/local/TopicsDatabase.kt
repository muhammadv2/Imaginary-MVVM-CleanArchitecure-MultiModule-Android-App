package com.developance.imaginary.topic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TopicEntity::class], version = 1)
abstract class RoomDatabase :RoomDatabase(){
    abstract val topicDao : TopicsDao
}
