package com.toren.data.repository

import com.toren.data.local.dao.AlarmDao
import com.toren.data.local.entity.toAlarm
import com.toren.data.local.entity.toAlarmEntity
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl
@Inject constructor(
    private val alarmDao: AlarmDao,
) : AlarmRepository {
    override suspend fun insertAlarm(alarm: Alarm): Long {
        return alarmDao.insertAlarm(alarm.toAlarmEntity())
    }

    override suspend fun getAlarms(): List<Alarm> {
        return alarmDao.getAlarms().map { it.toAlarm() }
    }

    override suspend fun deleteAlarm(id: Int): Int {
        return alarmDao.deleteAlarm(id)
    }

    override suspend fun updateAlarm(alarm: Alarm): Int {
        return alarmDao.updateAlarm(alarm.toAlarmEntity())
    }

    override suspend fun updateAlarmStateById(id: Int, enabled: Boolean): Int {
        return alarmDao.updateAlarmStatus(id, enabled)
    }
}