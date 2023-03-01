package com.developance.imaginary

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.developance.imaginary.photos.PhotoTaps
import com.developance.imaginary.photos.photos
import com.developance.imaginary.photos.presentation.detail.PhotoDetailsRoute
import com.developance.imaginary.topic.presentation.TopicsRoute

object MainDestinations {
    const val TOPICS_ROUTE = "topics"
    const val PHOTOS_ROUTE = "photos"
    const val PHOTO_ID = "photo_id"
    const val PHOTO_DETAILS_ROOT = "photo"
}

@Composable
fun NavGraph(
    finishActivity: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    destination: String,
    onBoardingComplete: () -> Unit,
) {
    val actions = remember(navController) { MainActions(navController) }

    if (destination.isBlank()) {
        SplashScreen()
    } else
        NavHost(
            navController = navController,
            startDestination = destination,
            modifier = modifier,
        ) {

            composable(MainDestinations.TOPICS_ROUTE) {
                TopicsRoute(
                    onSave = {
                        onBoardingComplete()
                        actions.onSelectingTopicsComlete()
                    })
            }

            navigation(
                route = MainDestinations.PHOTOS_ROUTE,
                startDestination = PhotoTaps.FEATURED_PHOTOS.route
            ) {
                photos(
                    snackBarHostState = snackBarHostState,
                    selectPhoto = actions.openPhotoDetails,
                    finishActivity = finishActivity,
                    configureTopics = actions.openTopicsSelecting
                )
            }

            composable(
                route = "${MainDestinations.PHOTO_DETAILS_ROOT}/{${MainDestinations.PHOTO_ID}}",
                arguments =
                listOf(navArgument(MainDestinations.PHOTO_ID) {
                    type = NavType.StringType
                })
            ) {
                PhotoDetailsRoute(
                    snackBarHostState = snackBarHostState,
                )
            }
        }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onSelectingTopicsComlete: () -> Unit = {
        navController.popBackStack()
        navController.navigate(MainDestinations.PHOTOS_ROUTE)
    }

    val openTopicsSelecting: () -> Unit = {
        navController.navigate(MainDestinations.TOPICS_ROUTE)
    }

    val openPhotoDetails = { photoId: String, from: NavBackStackEntry ->
        if (from.lifecycleIsResumed())
            navController.navigate("${MainDestinations.PHOTO_DETAILS_ROOT}/$photoId")
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(
                R.string.app_logo
            ),
            modifier.size(96.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(100.dp))
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
fun SplashScreenPrev() {
    SplashScreen()
}

@Preview(showBackground = true, widthDp = 300, heightDp = 300, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenDarkPrev() {
    SplashScreen()
}

@Preview
@Composable
fun NavGraphPreview() {
    NavGraph(
        finishActivity = { /*TODO*/ },
        snackBarHostState = SnackbarHostState(),
        destination = MainDestinations.PHOTOS_ROUTE,
        onBoardingComplete = {}
    )
}