@file:OptIn(ExperimentalMaterial3Api::class)

package com.developance.imaginary

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.developance.imaginary.common.ImaginaryGradiantBackground
import com.developance.imaginary.common.MainBackground
import com.developance.imaginary.photos.ImaginaryBottomBar
import com.developance.imaginary.photos.PhotoTaps
import java.util.*

@Composable
fun ImaginaryApp(
    finishActivity: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    MainBackground {
        ImaginaryGradiantBackground {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val tabs = remember { PhotoTaps.values() }
            val destination by mainViewModel.startDestination

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                containerColor = Color.Transparent,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                bottomBar = {
                    if (destination == MainDestinations.PHOTOS_ROUTE)
                        ImaginaryBottomBar(
                            navController = navController,
                            tabs,
                            Modifier.wrapContentHeight()
                        )
                }
            ) { innerPaddingModifier ->
                NavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier),
                    finishActivity = finishActivity,
                    snackBarHostState = snackbarHostState,
                    destination = destination,
                    onBoardingComplete = mainViewModel::onBoardingCompleted
                )
            }
        }
    }
}
