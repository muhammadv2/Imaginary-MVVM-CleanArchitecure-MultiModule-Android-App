package com.developance.imaginary.photos.data.di

import com.developance.imaginary.photos.data.remote.PhotosApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PhotosModule {

    @Singleton
    @Provides
    fun provideTopicsApi(retrofit: Retrofit): PhotosApiService =
        retrofit.create(PhotosApiService::class.java)
}
