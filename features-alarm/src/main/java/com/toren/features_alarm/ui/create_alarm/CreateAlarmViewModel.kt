package com.toren.features_alarm.ui.create_alarm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.repository.AlarmScheduler
import com.toren.domain.use_case.alarm.InsertAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateAlarmViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler, //repo ile değiştiricez
    private val insertAlarmUseCase: InsertAlarmUseCase
): ViewModel() {

    fun onEvent(event: CreateAlarmUiEvent) {
        when(event) {
            is CreateAlarmUiEvent.CreateAlarm -> {
                scheduleAlarm(
                    Alarm(
                        id = 0,
                        time = event.time,
                        message = event.message,
                        enabled = true
                    )
                )
            }
        }
    }



    fun scheduleAlarm(alarm: Alarm) {
        alarmScheduler.schedule(alarm).also {
            insertAlarmUseCase(alarm).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("SaveAlarm", "Saving alarm...")
                    }

                    is Resource.Success -> {
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