package com.toren.superapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toren.features_note.ui.screens.NoteListScreen
import com.toren.features_alarm.ui.graphs.AlarmScreens
import com.toren.features_alarm.ui.screens.AlarmDetailScreen
import com.toren.features_alarm.ui.screens.CreateAlarmScreen
import com.toren.features_alarm.ui.screens.AlarmListScreen
import com.toren.features_note.ui.graphs.NoteScreens
import com.toren.features_note.ui.screens.CreateNoteScreen
import com.toren.features_rocket.ui.screens.rocket_list.RocketListScreen
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
            NoteListScreen(navController)
        }
        composable(route = BottomBarScreens.Reminders.route) {
            AlarmListScreen(navController)
        }
        composable(route = BottomBarScreens.Launches.route) {
            RocketListScreen()
        }
        composable(route = AlarmScreens.CreateAlarm.route) {
            CreateAlarmScreen()
        }
        composable(route = AlarmScreens.AlarmDetail.route) {
            AlarmDetailScreen()
        }
        composable(route = NoteScreens.CreateNoteScreen.route) {
            CreateNoteScreen()
        }

    }
}
