package com.toren.features_alarm.ui.create_alarm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.repository.AlarmScheduler
import com.toren.domain.use_case.alarm.InsertAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateAlarmViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler, //use case ile değiştiricez
    private val insertAlarmUseCase: InsertAlarmUseCase
) : ViewModel() {

    private val _saveState = MutableStateFlow(false)
    val saveState: StateFlow<Boolean> = _saveState

    fun onEvent(event: CreateAlarmUiEvent) {
        when (event) {
            is CreateAlarmUiEvent.CreateAlarm -> {
                scheduleAlarm(
                    Alarm(
                        id = 0,
                        time = calculateAlarmTime(event.hour, event.minute, event.date),
                        message = event.message,
                        enabled = true
                    )
                )
            }
        }
    }

    private fun calculateAlarmTime(hour: Int, minute: Int, date: Long?): String {
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.timeInMillis = date
        } else {
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val finalDate = Date(calendar.timeInMillis)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(finalDate)
    }


    private fun scheduleAlarm(alarm: Alarm) {
        alarmScheduler.schedule(alarm).also {
            insertAlarmUseCase(alarm).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("SaveAlarm", "Saving alarm...")
                    }

                    is Resource.Success -> {
                        _saveState.value = true
                        Log.d("SaveAlarm", "Alarm saved with ID: ${result.data}")
                    }

                    is Resource.Error -> {
                        Log.e("SaveAlarm", "Error saving alarm: ${result.message}")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}