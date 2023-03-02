package com.developance.model

import com.developance.database.model.TopicEntity
import com.developance.network.model.NetworkTopic


fun NetworkTopic.asEntity() =

    TopicEntity(
        id = id,
        title = title,
        slug = slug,
        description = description,
        coverPhoto = coverPhoto.urls.thumb,
        totalPhotos = totalPhotos,
        isFavorite = false
    )

