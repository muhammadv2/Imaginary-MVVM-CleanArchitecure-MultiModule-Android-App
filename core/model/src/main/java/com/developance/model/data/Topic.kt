package com.developance.model.data

data class Topic(
    val id: String,
    val title: String,
    val slug: String,
    val description:String,
    val isFavorite: Boolean,
    val totalPhotos: Long,
    val coverPhoto: String?
)

val defaultRandomTopic = Topic(
    "1",
    "Random",
    "wallpapers",
    "Random",
    true,
    58,
    ""
)