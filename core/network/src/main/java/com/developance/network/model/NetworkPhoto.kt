package com.developance.network.model

import com.developance.model.data.ImageUrls
import com.developance.model.data.Links
import com.developance.model.data.Photographer
import com.developance.model.data.UserPhoto
import com.google.gson.annotations.SerializedName


data class Response(@SerializedName("results") val result: List<NetworkPhoto>)
data class NetworkPhoto(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val width: Long,
    val height: Long,
    val color: String,
    @SerializedName("blur_hash")
    val blurHash: String?,
    val likes: Long,
    val downloads: Long,
    val description: String?,
    val user: Photographer,
    val urls: ImageUrls,
    val location: com.developance.model.data.Location?,
    val links: Links,
)

fun NetworkPhoto.asDomain(): UserPhoto =
    UserPhoto(
        id = id,
        createdAt = createdAt,
        width = width,
        height = height,
        color = color,
        blurHash = blurHash ?: "",
        likes = likes,
        description = description ?: "",
        user = user,
        urls = urls,
        location = location,
        links = links,
        downloads = downloads
    )
