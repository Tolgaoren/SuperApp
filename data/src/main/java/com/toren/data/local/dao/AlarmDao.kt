package com.toren.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.toren.data.local.entity.AlarmEntity

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmEntity) : Long

    @Query("SELECT * FROM alarms")
    suspend fun getAlarms(): List<AlarmEntity>

    @Query("DELETE FROM alarms WHERE id IN (:ids)")
    suspend fun deleteAlarms(ids: List<Int>) : Int

    @Update
    suspend fun updateAlarm(alarm: AlarmEntity) : Int

    @Query("UPDATE alarms SET alarm_enabled = :isEnable WHERE id = :id")
    suspend fun updateAlarmStatus(id: Int, isEnable: Boolean): Int
}