package com.toren.features_alarm.ui.alarm_detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AlarmDetailScreen(
    navController: NavHostController,
    alarmId : Int
) {

    Text(text = "Alarm Detail Screen: $alarmId")

}