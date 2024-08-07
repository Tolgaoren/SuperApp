package com.toren.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toren.data.local.entity.AlarmEntity

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmEntity) : Long

    @Query("SELECT * FROM alarms")
    suspend fun getAlarms(): List<AlarmEntity>

    @Query("DELETE FROM alarms WHERE id = :id")
    suspend fun deleteAlarm(id: Int) : Int
}