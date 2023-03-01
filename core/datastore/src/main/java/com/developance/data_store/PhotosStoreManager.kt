package com.developance.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton //You can ignore this annotation as return `datastore` from `preferencesDataStore` is singletone
class PhotosStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    suspend fun addBookmarkedPhotoId(photoId: String) {

        settingsDataStore.edit { preference ->
            val newSet = preference[BOOKMARKED_PHOTOS_IDS_KEY]?.toMutableSet() ?: mutableSetOf()
            newSet.add(photoId)
            preference[BOOKMARKED_PHOTOS_IDS_KEY] = newSet.toSet()
        }
    }

    suspend fun removeBookmarkedPhotoId(photoId: String) {
        settingsDataStore.edit { preference ->
            val newSet = preference[BOOKMARKED_PHOTOS_IDS_KEY]?.toMutableSet()
            newSet?.let {
                newSet.remove(photoId)
                preference[BOOKMARKED_PHOTOS_IDS_KEY] = newSet.toSet()
            }

        }
    }

    val bookmarkedPhotosIds: Flow<Set<String>> = settingsDataStore.data.map { preferences ->
        preferences[BOOKMARKED_PHOTOS_IDS_KEY] ?: emptySet()
    }
}

private val Context.dataStore by preferencesDataStore("bookmarked_photos")
private val BOOKMARKED_PHOTOS_IDS_KEY = stringSetPreferencesKey("bookmarked_photos")