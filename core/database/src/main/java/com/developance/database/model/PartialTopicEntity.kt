package com.developance.database.model

import androidx.room.ColumnInfo

data class PartialTopicEntity(
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)