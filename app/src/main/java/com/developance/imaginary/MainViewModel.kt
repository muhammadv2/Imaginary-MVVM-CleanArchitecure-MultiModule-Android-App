package com.developance.imaginary

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developance.imaginary.data.local.UserPreferencesStoreManager
import com.developance.imaginary.photos.PhotoTaps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesStoreManager: UserPreferencesStoreManager,
) : ViewModel() {

    private val _startDestination =
        mutableStateOf("")
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            userPreferencesStoreManager.onBoardingCompleted.collect {
                if (it)
                    _startDestination.value = MainDestinations.PHOTOS_ROUTE
                else
                    _startDestination.value = MainDestinations.TOPICS_ROUTE
            }
        }
    }

    fun onBoardingCompleted() {
        viewModelScope.launch {
            userPreferencesStoreManager.toggleOnBoardingComplete()
        }
    }

}
enum class StartDestinationState{
    LOADING,TOPIC,PHOTOS
}