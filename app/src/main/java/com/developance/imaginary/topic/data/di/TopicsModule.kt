package com.developance.imaginary.topic.data.di

import android.content.Context
import androidx.room.Room
import com.developance.imaginary.topic.data.local.RoomDatabase
import com.developance.imaginary.topic.data.remote.TopicsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * When to scope?
Scoping a binding has a cost on both the generated code size and its runtime performance
so use scoping sparingly. The general rule for determining if a binding should be scoped
is to only scope the binding if it’s required for the correctness of the code. If you
think a binding should be scoped for purely performance reasons, first verify that the
performance is an issue, and if it is consider using @Reusable instead of a component scope.
 */
@Module
@InstallIn(SingletonComponent::class)
class TopicsModule {

    @Singleton
    @Provides
    fun provideTopicsApi(retrofit: Retrofit) : TopicsApiService=
        retrofit.create(TopicsApiService::class.java)

    @Singleton
    @Provides
    fun provideTopicsDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomDatabase::class.java, "topics_db")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideTopicsDao(roomDatabase: RoomDatabase)= roomDatabase.topicDao
}