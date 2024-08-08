package com.toren.domain.use_case.alarm

import com.toren.domain.Resource
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    operator fun invoke(): Flow<Resource<List<Alarm>>> = flow {
        emit(Resource.Loading())
        try {
            val alarms = repository.getAlarms()
            emit(Resource.Success(alarms))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}