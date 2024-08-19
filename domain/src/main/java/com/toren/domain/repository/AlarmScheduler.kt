package com.toren.domain.repository

import com.toren.domain.model.alarm.Alarm

interface AlarmScheduler {
    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}