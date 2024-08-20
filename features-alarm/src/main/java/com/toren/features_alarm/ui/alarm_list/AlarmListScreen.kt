package com.toren.features_alarm.ui.alarm_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.toren.features_alarm.ui.graphs.AlarmScreens

@Composable
fun AlarmListScreen(navController: NavHostController) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AlarmScreens.CreateAlarm.route)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    "New reminder action button."
                )
            }
        }
    }
}
