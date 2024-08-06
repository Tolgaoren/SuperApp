package com.toren.features_alarm.ui.graphs

sealed class AlarmScreens(
    val route: String,
    val title: String
) {
    object CreateAlarm : AlarmScreens(
        route = "CreateAlarm",
        title = "CreateAlarm"
    )

    object AlarmDetail : AlarmScreens(
        route = "AlarmDetail",
        title = "AlarmDetail"
    )

}