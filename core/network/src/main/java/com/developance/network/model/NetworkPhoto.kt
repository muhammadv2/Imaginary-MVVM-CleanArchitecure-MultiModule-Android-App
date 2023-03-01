package com.developance.network.model

import com.developance.model.data.ImageUrls
import com.developance.model.data.Links
import com.developance.model.data.Photographer
import com.developance.model.data.UserPhoto
import com.google.gson.annotations.SerializedName


data class Response(@SerializedName("results") val result: List<RemotePhoto>)
data class RemotePhoto(
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


fun List<RemotePhoto>.asDomain(): List<UserPhoto> =
    map {
        UserPhoto(
            id = it.id,
            createdAt = it.createdAt,
            width = it.width,
            height = it.height,
            color = it.color,
            blurHash = it.blurHash ?: "",
            likes = it.likes,
            description = it.description ?: "",
            user = it.user,
            urls = it.urls,
            links = it.links,
            location = it.location,
            downloads = it.downloads
        )
    }

fun RemotePhoto.asDomain(): UserPhoto =
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