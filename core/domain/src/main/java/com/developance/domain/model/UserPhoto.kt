package com.developance.domain.model

import com.developance.model.data.ImageUrls
import com.developance.model.data.Links
import com.developance.model.data.Location
import com.developance.model.data.Photographer


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
            regular = "",
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
            regular = "",
            thumb = ""
        ), null, Links("", ""),
        downloads = 122L

    ),
    UserPhoto(
        "vk_Z_ya4u14",
        "2023-02-28T03:02:31Z",
        6000,
        4000,
        "#c0c0c0",
        "L11{Tuay4nofoffQWBay4nj[_3WB",
        57,
        "Half Moon Photography",
        Photographer(
            "1",
            "Muhammad",

            ),
        ImageUrls(
            small =  "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=400",
            regular = "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=1080",
            thumb = "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=200",
            ), null, Links("", ""),
        downloads = 122L
    )
)
