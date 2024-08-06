package com.toren.superapp.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Rocket
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreens(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Notes : BottomBarScreens(
        route = "Notes",
        title = "Notes",
        icon = Icons.Outlined.Description,
        selectedIcon = Icons.Filled.Description
    )

    object Reminders : BottomBarScreens(
        route = "Alarms",
        title = "Alarms",
        icon = Icons.Outlined.Alarm,
        selectedIcon = Icons.Filled.Timer
    )

    object Launches : BottomBarScreens(
        route = "Rockets",
        title = "Rockets",
        icon = Icons.Outlined.Rocket,
        selectedIcon = Icons.Filled.Rocket

    )
}