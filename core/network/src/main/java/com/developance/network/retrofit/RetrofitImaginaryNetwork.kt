package com.developance.network.retrofit

import com.developance.imaginary.core.network.BuildConfig
import com.developance.network.ImaginaryNetworkDataSource
import com.developance.network.model.NetworkPhoto
import com.developance.network.model.NetworkTopic
import com.developance.network.model.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

const val ITEMS_PER_PAGE = 30
const val apiKey = BuildConfig.UNSPLASH_API_KEY

/**
 * Retrofit API declaration for Unsplash Network API
 */
interface RetrofitImaginaryNetworkApi {

    @GET("topics/{slug}/photos/?$apiKey")
    suspend fun fetchTopicPhotos(
        @Path("slug") slug: String?,
        @Query("page") page: Int?,
        @Query("client_id") accessKey: String = apiKey,
        @Query("per_page") perPage: Int = ITEMS_PER_PAGE
    ): List<NetworkPhoto>

    @GET("photos/{id}/?$apiKey")
    suspend fun fetchPhoto(
        @Path("id") id: String?,
        @Query("client_id") accessKey: String = apiKey
    ): NetworkPhoto

    @GET("search.json/photos/")
    suspend fun searchPhotos(
        @Query("query") query: String?,
        @Query("page") page: Int?,
        @Query("client_id") accessKey: String = apiKey,
        @Query("per_page") perPage: Int = ITEMS_PER_PAGE
    ): Response

    @GET("topics/?$apiKey")
    suspend fun fetchTopics(
        @Query("page") page: Int?,
        @Query("client_id") accessKey: String = apiKey,
        @Query("order_by") orderBy: String = "featured",
        @Query("per_Page") perPage: Int = ITEMS_PER_PAGE
    ): List<NetworkTopic>
}

/**
 * [Retrofit] backed [ImaginaryNetworkDataSource]
 */
@Singleton
class RetrofitImaginaryNetwork @Inject constructor(
    private val networkApi: RetrofitImaginaryNetworkApi
) : ImaginaryNetworkDataSource {

    override suspend fun fetchTopics(page: Int?): List<NetworkTopic> =
        networkApi.fetchTopics(page)


    override suspend fun fetchTopicPhotos(slug: String?, page: Int?): List<NetworkPhoto> =
        networkApi.fetchTopicPhotos(slug, page)

    override suspend fun fetchPhoto(id: String?): NetworkPhoto =
        networkApi.fetchPhoto(id)

    override suspend fun searchPhotos(query: String?, page: Int?): Response =
        networkApi.searchPhotos(query, page)

}