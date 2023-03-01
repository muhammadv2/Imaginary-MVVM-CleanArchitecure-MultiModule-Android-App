package com.developance.imaginary.photos.data.remote

import com.developance.imaginary.photos.domain.*
import com.developance.imaginary.topic.data.remote.ImageUrls
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
    val location: Location?,
    val links: Links,
)

data class Tag(
    val title: String,
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

data class Links(
    val download: String,
    val downloadLocation: String
)

data class Location(
    val city: String,
    val country: String,
)