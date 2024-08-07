package com.toren.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toren.data.local.dao.AlarmDao
import com.toren.data.local.dao.NoteDao
import com.toren.data.local.dao.RocketDao
import com.toren.data.local.entity.AlarmEntity
import com.toren.data.local.entity.FavoriteRocketEntity
import com.toren.data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class,
        AlarmEntity::class,
        FavoriteRocketEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun alarmDao(): AlarmDao
    abstract fun rocketDao(): RocketDao
}