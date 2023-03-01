package com.developance.imaginary.topic.domain

import com.developance.imaginary.topic.data.TopicsRepository
import javax.inject.Inject

class ToggleTopicUseCase @Inject constructor(
    private val repository: TopicsRepository
) {

    suspend operator fun invoke(id: String, oldValue: Boolean) {
        val newFav = oldValue.not()
        repository.toggleFavoriteTopic(id, newFav)
    }
}