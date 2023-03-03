package com.developance.model.data

data class Photo(
    val id: String,
    val createdAt: String,
    val width: Long,
    val height: Long,
    val color: String,
    val blurHash: String?,
    val likes: Long?,
    val downloads: Long?,
    val description: String?,
    val user: Photographer,
    val urls: ImageUrls,
    val location: Location?,
    val links: Links,
)