package com.developance.data_store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private const val USER_PREFERENCES_NAME = "USER_PREFERENCE"
private val USER_PREFERENCES_KEY = booleanPreferencesKey(USER_PREFERENCES_NAME)
private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

@Singleton //You can ignore this annotation as return `datastore` from `preferencesDataStore` is singletone
class UserPreferencesStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    suspend fun toggleOnBoardingComplete() {
        settingsDataStore.edit { preference ->
            preference[USER_PREFERENCES_KEY] = true
        }
    }

    val onBoardingCompleted: Flow<Boolean> = settingsDataStore.data.map { preferences ->
        preferences[USER_PREFERENCES_KEY] ?: false
    }
}