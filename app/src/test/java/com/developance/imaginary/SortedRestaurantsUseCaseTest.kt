package com.developance.imaginary

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SortedRestaurantsUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun getSortedRestaurants_IsItReallySorted() =
        scope.runTest {
            // Setup useCase
//            val restaurantsRepository = RestaurantsRepositoryImpl(
//                FakeApiService(),
//                FakeRoomDao()
//            )
//            val getSortedRestaurantsUseCase =
//                GetSortedTopicsUseCase(restaurantsRepository)
//            val getInitialRestaurantsUseCase =
//                GetInitialTopicsUseCase(restaurantsRepository, getSortedRestaurantsUseCase)
//
//            advanceUntilIdle()
//
//
//            //preload data
//            restaurantsRepository.loadRestaurants()
//            advanceUntilIdle()
//            val restaurants = DummyContent.getDomainRestaurants().sortedBy { it.title }
//
//            //assertion
//            assert(restaurants == getSortedRestaurantsUseCase() &&
//                    getInitialRestaurantsUseCase() == getSortedRestaurantsUseCase())
        }
}