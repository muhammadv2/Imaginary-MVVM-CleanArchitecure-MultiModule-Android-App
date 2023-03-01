package com.developance.imaginary.data.di

import android.security.AppUriAuthenticationPolicy
import com.developance.imaginary.data.di.Const.BASE_API_URL
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
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
class TopicsModule {

    @Singleton
    @Provides
    fun provivdeOkHttpClinetBuilder(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideResturantsApi(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(httpClient)
            .baseUrl(BASE_API_URL)
            .build()
}
