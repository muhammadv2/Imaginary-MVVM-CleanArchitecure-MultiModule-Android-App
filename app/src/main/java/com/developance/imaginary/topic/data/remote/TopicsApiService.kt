package com.developance.imaginary.topic.data.remote

import com.developance.imaginary.data.di.Const.CLIENT_ID_HEADER
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopicsApiService {

    @GET("topics/?$CLIENT_ID_HEADER")
    suspend fun fetchInterestingTopics(
        @Query("order_by") orderBy : String = "featured",
        @Query("page") page: String?,
        @Query("per_Page") perPage: Int = 20
    ): List<RemoteTopic>
}
