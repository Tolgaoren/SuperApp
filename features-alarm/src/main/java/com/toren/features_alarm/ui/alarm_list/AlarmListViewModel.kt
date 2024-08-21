package com.toren.features_alarm.ui.alarm_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.repository.AlarmScheduler
import com.toren.domain.use_case.alarm.DeleteAlarmUseCase
import com.toren.domain.use_case.alarm.GetAlarmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val getAlarmsUseCase: GetAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val scheduler: AlarmScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlarmListUiState())
    val uiState: StateFlow<AlarmListUiState> = _uiState

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
        }
    }

    private fun getAlarms() {
        getAlarmsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = AlarmListUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value = AlarmListUiState(alarms = result.data ?: emptyList())
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
                        _uiState.value = AlarmListUiState(isLoading = true)
                    }
                    is Resource.Success -> {
                        getAlarms()
                    }

                    is Resource.Error -> {
                        _uiState.value = AlarmListUiState(error = result.message ?: "Error")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}