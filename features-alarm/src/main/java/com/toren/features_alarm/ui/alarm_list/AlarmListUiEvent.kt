package com.toren.features_alarm.ui.alarm_list

import com.toren.domain.model.alarm.Alarm

sealed class AlarmListUiEvent{
    data class DeleteAlarm(val alarmId: Int) : AlarmListUiEvent()
    object Refresh : AlarmListUiEvent()
    data class ReverseAlarmState(val alarm: Alarm) : AlarmListUiEvent()

}