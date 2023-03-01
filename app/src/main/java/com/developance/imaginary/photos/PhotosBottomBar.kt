package com.developance.imaginary.photos

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.developance.imaginary.ImaginaryBottomBarNavigation
import com.developance.imaginary.BottomBarItem
import com.developance.imaginary.ui.theme.Icon


@Composable
fun ImaginaryBottomBar(
    navController: NavController,
    tabs: Array<PhotoTaps>,
    modifier: Modifier = Modifier
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: PhotoTaps.FEATURED_PHOTOS.route

    val destination = remember { PhotoTaps.values().map { it.route } }
    if (currentRoute in destination) {
        ImaginaryBottomBarNavigation(
            modifier = modifier
        ) {
            tabs.forEach { tab ->
                val selected = currentRoute == tab.route

                BottomBarItem(
                    selected = selected,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        val icon = if (selected) {
                            tab.selected
                        } else {
                            tab.unSelected
                        }
                        when (icon) {
                            is Icon.ImageVectorIcon -> Icon(
                                imageVector = icon.imageVector,
                                contentDescription = null
                            )

                            is Icon.DrawableResourceIcon -> Icon(
                                painter = painterResource(id = icon.id),
                                contentDescription = null
                            )
                        }
                    })
            }
        }
    }
}

