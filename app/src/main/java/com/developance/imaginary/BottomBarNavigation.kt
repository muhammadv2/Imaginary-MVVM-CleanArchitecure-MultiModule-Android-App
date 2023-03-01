package com.developance.imaginary

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ImaginaryBottomBarNavigation(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        contentColor = ImaginaryNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

@Composable
fun RowScope.BottomBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = ImaginaryNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = ImaginaryNavigationDefaults.navigationContentColor(),
            indicatorColor = ImaginaryNavigationDefaults.navigationIndicatorColor()
        )
    )
}

object ImaginaryNavigationDefaults {
    @Composable
    fun navigationContentColor() =
        androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() =
        androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() =
        androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer
}

