package com.toren.features_reminder.ui.graphs

sealed class ReminderScreens(
    val route: String,
    val title: String
) {
    object CreateReminder : ReminderScreens(
        route = "CreateReminders",
        title = "CreateReminders"
    )

    object ReminderDetail : ReminderScreens(
        route = "ReminderDetail",
        title = "ReminderDetail"
    )

}