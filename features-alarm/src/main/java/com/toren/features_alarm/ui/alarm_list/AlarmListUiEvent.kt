package com.toren.features_alarm.ui.alarm_list

sealed class AlarmListUiEvent{
    data class DeleteAlarm(val alarmId: Int) : AlarmListUiEvent()
    object Refresh : AlarmListUiEvent()
    data class ReverseAlarmState(val alarmId: Int) : AlarmListUiEvent()

}