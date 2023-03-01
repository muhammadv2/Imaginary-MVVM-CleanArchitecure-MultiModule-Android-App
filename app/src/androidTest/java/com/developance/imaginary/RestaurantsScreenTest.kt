//package com.developance.imaginary
//
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.ComposeContentTestRule
//import androidx.compose.ui.test.junit4.createComposeRule
//import com.developance.imaginary.onboarding.data.dummy.DummyContent
//import com.developance.imaginary.onboarding.presentation.list.RestaurantsScreen
//import com.developance.imaginary.onboarding.presentation.list.TopicsUiState
//import com.developance.imaginary.ui.theme.ImaginaryTheme
//import org.junit.Rule
//import org.junit.Test
//
////This is using [JUnit] testing framework
//class RestaurantsScreenTest {
//
//    /**
//     * To run our Compose UI tests, we are using the JUnit testing framework that will
//    allow us to write repeatable unit tests in an isolated environment with the help of a
//    test rule. Test rules allow us to add functionality to all the tests within a test class.
//     */
//    @get:Rule
//    val testRule: ComposeContentTestRule =
//        createComposeRule() //ComposeContentTestRule will not only allow us to set the Compose UI under test but also host tests on an Android device, while also giving us the ability to interact with the composables under test or perform UI assertions.
//
//    /**
//     * To tell the JUnit testing library to run an individual test for this method, we've
//    annotated it with the @Test annotation.
//     */
//    @Test
//    fun initialState_isRendered() {
//        testRule.setContent {
//            ImaginaryTheme {
//                RestaurantsScreen(state = TopicsUiState(
//                    restaurants = listOf(),
//                    true
//                ), onItemClick = {}, onFavoriteClick = { _, _ -> })
//            }
//        }
//
//        testRule.onNodeWithContentDescription(
//            Description.RESTAURANTS_LOADING
//        ).assertIsDisplayed()
//    }
//
//    @Test
//    fun stateWithContent_isRendered() {
//        testRule.setContent {
//            ImaginaryTheme {
//                RestaurantsScreen(state = TopicsUiState(
//                    restaurants = DummyContent.getDomainRestaurants(),
//                    false
//                ), onItemClick = {}, onFavoriteClick = { _, _ -> })
//            }
//        }
//
//        testRule.onNodeWithText(DummyContent.getDomainRestaurants()[0].title)
//            .assertIsDisplayed()
//        testRule.onNodeWithText(DummyContent.getDomainRestaurants()[0].description)
//            .assertIsDisplayed()
//
//        testRule.onNodeWithContentDescription(
//            Description.RESTAURANTS_LOADING
//        ).assertDoesNotExist()
//    }
//
//    @Test
//    fun stateWithError_isRendered() {
//        testRule.setContent {
//            ImaginaryTheme {
//                RestaurantsScreen(state = TopicsUiState(
//                    emptyList(),
//                    false,
//                    errorMessage = "Here's an error"
//                ), onItemClick = {}, onFavoriteClick = { _, _ -> })
//            }
//        }
//        testRule.onNodeWithText("Here's an error").assertIsDisplayed()
//        testRule.onNodeWithContentDescription(Description.RESTAURANTS_LOADING).assertDoesNotExist()
//    }
//
//    @Test
//    fun stateWithContent_ClickOnItem_isRegistered(){
//        val restaurants = DummyContent.getDomainRestaurants()
//        val targetRestaurant = restaurants[2]
//
//        testRule.setContent {
//            ImaginaryTheme() {
//                RestaurantsScreen(
//                    state = TopicsUiState(
//                        restaurants = restaurants,
//                        isLoading = false),
//                    onFavoriteClick = { _, _ -> },
//                    onItemClick = { id -> assert(id == targetRestaurant.id)  })
//            }
//        }
//        testRule.onNodeWithText(targetRestaurant.title).performClick()
//    }
//}