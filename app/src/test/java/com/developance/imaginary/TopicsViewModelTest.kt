package com.developance.imaginary

import com.developance.imaginary.topic.domain.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class TopicsViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()

//    @Before
//    fun setUp() {
//        Dispatchers.setMain(dispatcher = dispatcher)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
//    }
//
//    /**
//     * make use of the JUnit testing library to define and run
//    individual unit tests for each method annotated with the @Test annotation.
//     */
//    @Test
//    fun initialState_isProduced() =
//        runTest {
//            val viewModel = getViewModel()
//            val initalState = viewModel.state
//
//            assert(
//                initalState == TopicsUiState(
//                    topics = emptyList(),
//                    isLoading = true,
//                    errorMessage = null
//                )
//            )
//        }
//
//    @Test
//    fun stateWithContent_isProduced() = runTest {
//        val testVM = getViewModel()
//        advanceUntilIdle()
//        val currentState = testVM.state
//        assert(
//            currentState == TopicsUiState(
//                topics =
//                DummyContent.topics,
//                isLoading = false,
//                errorMessage = null
//            )
//        )
//    }

}

