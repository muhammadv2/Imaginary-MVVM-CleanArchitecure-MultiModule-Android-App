package com.developance.network.model

import com.developance.model.data.CoverPhoto
import com.google.gson.annotations.SerializedName

data class RemoteTopic(
    val id: String,
    val slug: String,
    val title: String,
    val description: String,
    @SerializedName("total_photos")
    val totalPhotos: Long,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto,
)

fun List<RemoteTopic>.asEntities() =
    map {
//        TopicEntity(
//            id = it.id,
//            title = it.title,
//            slug = it.slug,
//            description = it.description,
//            coverPhoto = it.coverPhoto.urls.thumb,
//            totalPhotos = it.totalPhotos,
//            isFavorite = false
//        )
    }


