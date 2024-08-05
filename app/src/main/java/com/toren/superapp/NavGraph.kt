package com.toren.superapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toren.features_note.ui.screens.NotesScreen
import com.toren.features_reminder.ui.graphs.ReminderScreens
import com.toren.features_reminder.ui.screens.CreateReminderScreen
import com.toren.features_reminder.ui.screens.RemindersScreen
import com.toren.features_rocket.ui.screens.RocketsScreen
import com.toren.superapp.ui.bottombar.BottomBarScreens

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Notes.route,
        modifier = modifier
    ) {
        composable(route = BottomBarScreens.Notes.route) {
            NotesScreen(navController)
        }
        composable(route = BottomBarScreens.Reminders.route) {
            RemindersScreen(navController)
        }
        composable(route = BottomBarScreens.Launches.route) {
            RocketsScreen()
        }
        composable(route = ReminderScreens.CreateReminder.route) {
            CreateReminderScreen()
        }
    }
}
