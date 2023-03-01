package com.developance.model.data

data class UserPhoto(
    val id: String,
    val createdAt: String,
    val width: Long,
    val height: Long,
    val color: String,
    val blurHash: String?,
    val likes: Long,
    val description: String?,
    val user: Photographer,
    val urls: ImageUrls,
    val location: Location?,
    val links: Links,
    val downloads: Long?
)


fun previewPhotos() = listOf(
    UserPhoto(
        "2",
        "12/12/2020",
        2000,
        2000,
        "red",
        "BH2",
        2000,
        "fake photo2",
        Photographer(
            "1",
            "Muhammad",
        ),
        urls = ImageUrls(
            small = "https://images.unsplash.com/photo-1519999482648-25049ddd37b1",
            raw = "",
            regular = "",
            full = "",
            thumb = ""
        ),
        location = Location("japan", "japan"),
        Links("", ""),
        downloads = 122L
    ),
    UserPhoto(
        "2",
        "",
        2000,
        2000,
        "red",
        "BH2",
        2000,
        "fake photo1",

        Photographer(
            "1",
            "Muhammad",
        ),
        ImageUrls(
            small = "https://images.unsplash.com/photo-1519999482648-25049ddd37b1",
            raw = "",
            regular = "",
            full = "",
            thumb = ""
        ), null, Links("", ""),
        downloads = 122L

    ),
    UserPhoto(
        "2",
        "",
        2000,
        2000,
        "red",
        "BH2",
        2000,
        "fake photo1",
        Photographer(
            "1",
            "Muhammad",

        ),
        ImageUrls(
            small = "https://images.unsplash.com/photo-1519999482648-25049ddd37b1",
            raw = "",
            regular = "",
            full = "",
            thumb = ""
        ), null, Links("", ""),
        downloads = 122L
    )
)
