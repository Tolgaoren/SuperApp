package com.toren.domain.use_case.alarm

import com.toren.domain.Resource
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    operator fun invoke(alarm: Alarm) : Flow<Resource<Int>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.updateAlarm(alarm)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}