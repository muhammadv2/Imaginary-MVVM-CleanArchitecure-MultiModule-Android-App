package com.developance.network.model

import com.developance.model.data.CoverPhoto
import com.google.gson.annotations.SerializedName

data class NetworkTopic(
    val id: String,
    val slug: String,
    val title: String,
    val description: String,
    @SerializedName("total_photos")
    val totalPhotos: Long,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto,
)
