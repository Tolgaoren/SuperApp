package com.toren.features_alarm.ui.create_alarm

sealed class CreateAlarmUiEvent {
    data class CreateAlarm(val time: Long, val message: String) : CreateAlarmUiEvent()
}