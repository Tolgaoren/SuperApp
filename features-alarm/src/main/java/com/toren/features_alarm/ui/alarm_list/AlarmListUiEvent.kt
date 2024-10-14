package com.toren.features_alarm.ui.alarm_list

import com.toren.domain.model.alarm.Alarm

sealed class AlarmListUiEvent{
    object Refresh : AlarmListUiEvent()
    data class ReverseAlarmState(val alarm: Alarm) : AlarmListUiEvent()
    data class OnAlarmSelected(val alarm: Alarm) : AlarmListUiEvent()
    object SelectionModeChanged: AlarmListUiEvent()
    object OnAlarmsDeleted : AlarmListUiEvent()
}