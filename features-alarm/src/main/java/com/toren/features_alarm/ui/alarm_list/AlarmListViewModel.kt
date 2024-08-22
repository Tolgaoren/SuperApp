package com.toren.features_alarm.ui.alarm_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.repository.AlarmScheduler
import com.toren.domain.use_case.alarm.DeleteAlarmUseCase
import com.toren.domain.use_case.alarm.GetAlarmsUseCase
import com.toren.domain.use_case.alarm.ReverseAlarmUseCase
import com.toren.util.toFormatedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val getAlarmsUseCase: GetAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val reverseAlarmUseCase: ReverseAlarmUseCase,
    private val scheduler: AlarmScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlarmListUiState())
    val uiState: StateFlow<AlarmListUiState> = _uiState

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> = _alarms

    init {
        getAlarms()
    }

    fun onEvent(event: AlarmListUiEvent) {
        when (event) {
            is AlarmListUiEvent.DeleteAlarm -> {
                deleteAlarm(event.alarmId)
            }

            is AlarmListUiEvent.Refresh -> {
                getAlarms()
            }

            is AlarmListUiEvent.ReverseAlarmState -> {
                reverseAlarmState(event.alarmId)
            }
        }
    }

    private fun getAlarms() {
        getAlarmsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = AlarmListUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value = AlarmListUiState(alarms = formatAlarms(result.data ?: emptyList()))
                    _alarms.value = result.data ?: emptyList()
                }

                is Resource.Error -> {
                    _uiState.value = AlarmListUiState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteAlarm(alarmId: Int) {
        scheduler.cancel(alarmId).also {
            deleteAlarmUseCase(alarmId).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("AlarmListViewModel", "Loading...")
                    }

                    is Resource.Success -> {
                        getAlarms()
                    }

                    is Resource.Error -> {
                        Log.d("AlarmListViewModel", "Error: ${result.message}")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun reverseAlarmState(id: Int) {
        reverseAlarmUseCase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("AlarmListViewModel", "Loading...")
                }

                is Resource.Success -> {
                    _alarms.update {
                        it.map { alarm ->
                            if (alarm.id == id) {
                                if (alarm.enabled) {
                                    scheduler.cancel(id)
                                } else {
                                    scheduler.schedule(alarm)
                                }
                                alarm.copy(enabled = !alarm.enabled)
                            } else {
                                alarm
                            }
                        }
                    }
                    _uiState.update { currentState ->
                        val updatedAlarms = currentState.alarms.map { alarm ->
                            if (alarm.id == id) {
                                alarm.copy(enabled = !alarm.enabled)
                            } else {
                                alarm
                            }
                        }
                        currentState.copy(alarms = updatedAlarms)
                    }
                }

                is Resource.Error -> {
                    Log.d("AlarmListViewModel", "Error: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun formatAlarms(alarms: List<Alarm>): List<Alarm> {
        return alarms.map { alarm ->
            alarm.copy(time = alarm.time.toFormatedDate())
        }
    }


}