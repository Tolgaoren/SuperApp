package com.toren.superapp.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Notes : BottomBarScreen(
        route = "Notes",
        title = "Notes",
        icon = Icons.Outlined.Create,
        selectedIcon = Icons.Filled.Create
    )

    object Reminders : BottomBarScreen(
        route = "Reminders",
        title = "Reminders",
        icon = Icons.Outlined.DateRange,
        selectedIcon = Icons.Filled.DateRange
    )

    object Launches : BottomBarScreen(
        route = "Launches",
        title = "Launches",
        icon = Icons.Outlined.Info,
        selectedIcon = Icons.Filled.Info

    )
}