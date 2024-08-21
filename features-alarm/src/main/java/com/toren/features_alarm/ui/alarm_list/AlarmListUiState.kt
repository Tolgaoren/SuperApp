package com.toren.features_alarm.ui.alarm_list

import com.toren.domain.model.alarm.Alarm

data class AlarmListUiState(
    val alarms: List<Alarm> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)