package com.developance.network.di

import com.developance.network.retrofit.RetrofitImaginaryNetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provivdesOkHttpClinetBuilder(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return httpClient.build()
    }

    @Singleton
    @Provides
    fun providesRetrofitObject(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(httpClient)
            .baseUrl("https://api.unsplash.com/")
            .build()

    @Singleton
    @Provides
    fun providesImaginaryApi(retrofit: Retrofit): RetrofitImaginaryNetworkApi =
        retrofit.create(RetrofitImaginaryNetworkApi::class.java)

}