package com.toren.features_alarm.ui.create_alarm

sealed class CreateAlarmUiEvent {
    data class CreateAlarm(
        val hour: Int,
        val minute: Int,
        val message: String,
        val date: Long? = null,
    ) : CreateAlarmUiEvent()
}