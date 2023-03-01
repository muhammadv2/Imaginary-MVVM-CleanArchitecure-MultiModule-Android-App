package com.developance.model

import com.developance.database.model.TopicEntity
import com.developance.network.model.RemoteTopic


fun List<RemoteTopic>.asEntities() =
    map {
        TopicEntity(
            id = it.id,
            title = it.title,
            slug = it.slug,
            description = it.description,
            coverPhoto = it.coverPhoto.urls.thumb,
            totalPhotos = it.totalPhotos,
            isFavorite = false
        )
    }


