package com.toren.superapp.ui.bottombar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Notes.route
    ) {
        composable(route = BottomBarScreen.Notes.route) {
            //NotesScreen()
            NavBox(text = "Notes")
        }
        composable(route = BottomBarScreen.Reminders.route) {
            //RemindersScreen()
            NavBox(text = "Reminders")
        }
        composable(route = BottomBarScreen.Launches.route) {
            //LaunchesScreen()
            NavBox(text = "Launches")
        }
    }
}

@Composable
fun NavBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}
