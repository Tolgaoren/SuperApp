package com.toren.domain.repository

import com.toren.domain.model.alarm.Alarm

interface AlarmRepository {
    suspend fun insertAlarm(alarm: Alarm): Long

    suspend fun getAlarms(): List<Alarm>

    suspend fun deleteAlarm(id: Int): Int

    suspend fun updateAlarm(alarm: Alarm): Int

    suspend fun updateAlarmStateById(id: Int, enabled: Boolean): Int
}