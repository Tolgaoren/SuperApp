package com.toren.features_alarm.ui.alarm_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.repository.AlarmScheduler
import com.toren.domain.use_case.alarm.DeleteAlarmsUseCase
import com.toren.domain.use_case.alarm.GetAlarmsUseCase
import com.toren.domain.use_case.alarm.UpdateAlarmUseCase
import com.toren.domain.util.toFormatedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val getAlarmsUseCase: GetAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmsUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val scheduler: AlarmScheduler,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlarmListUiState())
    val uiState: StateFlow<AlarmListUiState> = _uiState

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> = _alarms

    private val _selectedAlarms = mutableStateOf(setOf<Alarm>())
    val selectedAlarms: State<Set<Alarm>> = _selectedAlarms

    private val _isItemsSelectable = mutableStateOf(false)
    val isItemsSelectable: State<Boolean> = _isItemsSelectable

    init {
        getAlarms()
    }

    fun onEvent(event: AlarmListUiEvent) {
        when (event) {
            is AlarmListUiEvent.Refresh -> {
                getAlarms()
            }

            is AlarmListUiEvent.ReverseAlarmState -> {
                val alarm = _alarms.value.find { it.id == event.alarm.id }
                alarm?.let {
                    updateAlarm(
                        Alarm(
                            id = it.id,
                            time = updatedDate(it),
                            message = it.message,
                            enabled = it.enabled.not()
                        )
                    )
                }
            }

            is AlarmListUiEvent.OnAlarmSelected -> {
                if (_selectedAlarms.value.contains(event.alarm).not()) {
                    _selectedAlarms.value += event.alarm
                } else {
                    _selectedAlarms.value -= event.alarm
                }
            }

            is AlarmListUiEvent.SelectionModeChanged -> {
                _isItemsSelectable.value = !_isItemsSelectable.value
                if (_isItemsSelectable.value.not()) {
                    _selectedAlarms.value = emptySet()
                }
            }

            is AlarmListUiEvent.OnAlarmsDeleted -> {
                _selectedAlarms.value.forEach {
                    scheduler.cancel(it.id)
                }.also {
                    deleteAlarms(
                        _selectedAlarms.value.map { alarm ->
                            alarm.id
                        }
                    )
                }
            }
        }
    }

    private fun updatedDate(alarm: Alarm): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = dateFormat.parse(alarm.time)
        date?.let {
            val currentDate = Date(System.currentTimeMillis())
            if (date.before(currentDate)) {
                val calendar = Calendar.getInstance()
                calendar.time = date

                val currentCalendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR))
                calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH))
                calendar.set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH))

                if (calendar.time.before(currentDate)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                }
                val newDate = calendar.time
                return dateFormat.format(newDate)
            }
        }
        return alarm.time
    }

    private fun getAlarms() {
        getAlarmsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = AlarmListUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value =
                        AlarmListUiState(alarms = formatAlarms(result.data ?: emptyList()))
                    _alarms.value = result.data ?: emptyList()
                }

                is Resource.Error -> {
                    _uiState.value = AlarmListUiState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteAlarms(ids: List<Int>) {
        deleteAlarmUseCase(ids).onEach { result ->
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

    private fun updateAlarm(renewedAlarm: Alarm) {
        println(renewedAlarm.time)
        updateAlarmUseCase(renewedAlarm).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("AlarmListViewModel", "Loading...")
                }

                is Resource.Success -> {
                    Log.d("AlarmListViewModel", "Success: ${result.data}")
                    _alarms.update {
                        it.map { alarm ->
                            if (alarm.id == renewedAlarm.id) {
                                if (alarm.enabled) {
                                    scheduler.cancel(renewedAlarm.id)
                                } else {
                                    scheduler.schedule(renewedAlarm)
                                }
                                alarm.copy(enabled = !alarm.enabled)
                            } else {
                                alarm
                            }
                        }
                    }
                    _uiState.update { currentState ->
                        val updatedAlarms = currentState.alarms.map { alarm ->
                            if (alarm.id == renewedAlarm.id) {
                                alarm.copy(
                                    time = renewedAlarm.time.toFormatedDate(),
                                    enabled = !alarm.enabled
                                )
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