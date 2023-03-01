package com.developance.imaginary

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ToggleRestaurantUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    @Test
    fun toggleRestaurant_IsUpdatingFavoriteField() =
        scope.runTest {
//            // Setup useCase
//            val restaurantsRepository = RestaurantsRepositoryImpl(
//                FakeApiService(),
//                FakeRoomDao())
//            val getSortedRestaurantsUseCase =
//                GetSortedTopicsUseCase(restaurantsRepository)
//            val useCase = ToggleTopicUseCase(
//                restaurantsRepository,
//                getSortedRestaurantsUseCase)
//            // Preload data
//            restaurantsRepository.loadRestaurants()
//            advanceUntilIdle()
//            // Execute useCase
//            val restaurants = DummyContent.getDomainRestaurants()
//            val targetItem = restaurants[0]
//            val isFavorite = targetItem.isFavorite
//            val updatedRestaurants = useCase(
//                targetItem.id,
//                isFavorite
//            )
//            advanceUntilIdle()
//            // Assertion
//            restaurants[0] = targetItem.copy(isFavorite = !isFavorite)
//            assert(updatedRestaurants == restaurants)
//        }
}}