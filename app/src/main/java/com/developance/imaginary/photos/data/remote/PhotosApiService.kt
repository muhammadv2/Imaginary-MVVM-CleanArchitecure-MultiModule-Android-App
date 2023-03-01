package com.developance.imaginary.photos.data.remote

import com.developance.imaginary.data.di.Const.CLIENT_ID_HEADER
import com.developance.imaginary.photos.domain.UserPhoto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val ITEMS_PER_PAGE = 30

interface PhotosApiService {

    @GET("topics/{slug}/photos/?$CLIENT_ID_HEADER")
    suspend fun fetchTopicPhotos(
        @Path("slug") slug: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = ITEMS_PER_PAGE
    ): List<RemotePhoto>

    @GET("photos/{id}/?$CLIENT_ID_HEADER")
    suspend fun fetchPhoto(
        @Path("id") id: String
    ): RemotePhoto

    @GET("search/photos/?$CLIENT_ID_HEADER")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = ITEMS_PER_PAGE
    ): Response
}