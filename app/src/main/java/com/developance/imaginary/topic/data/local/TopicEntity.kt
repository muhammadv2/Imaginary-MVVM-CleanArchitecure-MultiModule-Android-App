package com.developance.imaginary.topic.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developance.imaginary.topic.domain.UserTopic

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "slug")
    val slug: String,
    @ColumnInfo(name = "total_photos")
    val totalPhotos: Long,
    @ColumnInfo(name = "cover_photo")
    val coverPhoto: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "description")
    val description: String
)

data class PartialTopicEntity(
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)


fun List<TopicEntity>.asExternalModel(): List<UserTopic> {
    return map {
        UserTopic(
            id = it.id,
            title = it.title,
            slug = it.slug,
            totalPhotos = it.totalPhotos,
            coverPhoto = it.coverPhoto,
            isFavorite = it.isFavorite,
            description = it.description
        )
    }
}

fun TopicEntity.asExternalModel() = UserTopic(
    id = id,
    title = title,
    slug = slug,
    totalPhotos = totalPhotos,
    coverPhoto = coverPhoto,
    isFavorite = isFavorite,
    description = description
)
