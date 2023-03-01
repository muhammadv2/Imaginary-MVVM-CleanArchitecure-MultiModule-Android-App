package com.developance.imaginary.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher


@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @MainDispatcher
    @Singleton
    @Provides
    fun provideMainDispatcher() :CoroutineDispatcher= Dispatchers.Main

    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatcher() : CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Singleton
    @Provides
    fun provideDefaultDispatcher() : CoroutineDispatcher = Dispatchers.Default
}