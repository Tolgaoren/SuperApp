package com.toren.superapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.toren.features_note.ui.note_list.NoteListScreen
import com.toren.features_alarm.ui.graphs.AlarmScreens
import com.toren.features_alarm.ui.alarm_detail.AlarmDetailScreen
import com.toren.features_alarm.ui.create_alarm.CreateAlarmScreen
import com.toren.features_alarm.ui.alarm_list.AlarmListScreen
import com.toren.features_note.ui.graphs.NoteScreens
import com.toren.features_note.ui.create_note.CreateNoteScreen
import com.toren.features_note.ui.note_detail.NoteDetailScreen
import com.toren.features_rocket.ui.favorite_rockets.FavoriteRocketsScreen
import com.toren.features_rocket.ui.graphs.RocketScreens
import com.toren.features_rocket.ui.rocket_detail.RocketDetailScreen
import com.toren.features_rocket.ui.rocket_list.RocketListScreen
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
            NoteListScreen(navController = navController)
        }
        composable(route = BottomBarScreens.Reminders.route) {
            AlarmListScreen(navController)
        }
        composable(route = BottomBarScreens.Launches.route) {
            RocketListScreen(navController = navController)
        }
        composable(route = AlarmScreens.CreateAlarm.route) {
            CreateAlarmScreen(navController = navController)
        }
        composable(
            route = AlarmScreens.AlarmDetail.route + "/{alarmId}",
            arguments = listOf(navArgument("alarmId") {
                type = NavType.IntType
            })
        ) {
            val alarmId = it.arguments?.getInt("alarmId") ?: -1
            AlarmDetailScreen(
                navController = navController,
                alarmId = alarmId
            )
        }
        composable(route = NoteScreens.CreateNoteScreen.route) {
            CreateNoteScreen(navController = navController)
        }
        composable(
            route = NoteScreens.NoteDetailScreen.route + "/{noteId}",
            arguments = listOf(navArgument("noteId") {
                type = NavType.IntType
            })
        ) {
            val noteId = it.arguments?.getInt("noteId") ?: -1
            NoteDetailScreen(
                navController = navController,
                noteId = noteId
            )
        }
        composable(route = RocketScreens.RocketListScreen.route) {
            RocketListScreen(
                navController = navController
            )
        }
        composable(route = RocketScreens.RocketDetailScreen.route + "/{rocketId}") {
            val rocketId = it.arguments?.getString("rocketId")
            RocketDetailScreen(
                rocketId = rocketId.toString()
            )
        }
        composable(route = RocketScreens.FavoriteRocketsScreen.route) {
            FavoriteRocketsScreen(
                navController = navController
            )
        }
    }
}
