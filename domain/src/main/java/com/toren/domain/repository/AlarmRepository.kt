package com.toren.domain.repository

import com.toren.domain.model.alarm.Alarm

interface AlarmRepository {
    suspend fun insertAlarm(alarm: Alarm): Long

    suspend fun getAlarms(): List<Alarm>

    suspend fun deleteAlarm(id: Int): Int

    suspend fun reverseAlarm(id: Int): Int
}