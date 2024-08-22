package com.toren.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toren.domain.model.alarm.Alarm
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "alarm_time") val time: String,
    @ColumnInfo(name = "alarm_message") val message: String,
    @ColumnInfo(name = "alarm_enabled") val enabled: Boolean = true,
/*
    @ColumnInfo(name = "alarm_repeat") val repeat: Boolean,
    @ColumnInfo(name = "alarm_repeat_interval") val repeatInterval: Int,
    @ColumnInfo(name = "alarm_repeat_days") val repeatDays: List<String>,
    @ColumnInfo(name = "alarm_sound") val sound: String*/
)

fun AlarmEntity.toAlarm(): Alarm {
    return Alarm(
        id = id,
        time = time,
        message = message,
        enabled = enabled
    )
}

fun Alarm.toAlarmEntity(): AlarmEntity {
    return AlarmEntity(
        id = id,
        time = time,
        message = message,
        enabled = enabled
    )
}