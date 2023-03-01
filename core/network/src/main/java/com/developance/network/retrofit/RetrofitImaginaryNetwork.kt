package com.developance.network.retrofit

import com.developance.network.ImaginaryNetworkDataSource
import com.developance.network.model.RemotePhoto
import com.developance.network.model.RemoteTopic
import com.developance.network.model.Response
import com.google.samples.apps.nowinandroid.core.network.BuildConfig
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
    ): List<RemotePhoto>

    @GET("photos/{id}/?$apiKey")
    suspend fun fetchPhoto(
        @Path("id") id: String?,
        @Query("client_id") accessKey: String = apiKey
    ): RemotePhoto

    @GET("search/photos/")
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
    ): List<RemoteTopic>
}

/**
 * [Retrofit] backed [ImaginaryNetworkDataSource]
 */
@Singleton
class RetrofitImaginaryNetwork @Inject constructor(
    private val networkApi: RetrofitImaginaryNetworkApi
) : ImaginaryNetworkDataSource {

    override suspend fun fetchTopics(page: Int?): List<RemoteTopic> =
        networkApi.fetchTopics(page)


    override suspend fun fetchTopicPhotos(slug: String?, page: Int?): List<RemotePhoto> =
        networkApi.fetchTopicPhotos(slug, page)

    override suspend fun fetchPhoto(id: String?): RemotePhoto =
        networkApi.fetchPhoto(id)

    override suspend fun searchPhotos(query: String?, page: Int?): Response =
        networkApi.searchPhotos(query, page)

}