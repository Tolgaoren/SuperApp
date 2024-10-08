package com.toren.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.toren.data.local.TypeConverter
import com.toren.data.local.dao.AlarmDao
import com.toren.data.local.dao.FavoriteRocketDao
import com.toren.data.local.dao.NoteDao
import com.toren.data.local.dao.RocketDao
import com.toren.data.local.entity.AlarmEntity
import com.toren.data.local.entity.FavoriteRocketEntity
import com.toren.data.local.entity.RocketEntity
import com.toren.data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class,
        AlarmEntity::class,
        RocketEntity::class,
        FavoriteRocketEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun alarmDao(): AlarmDao
    abstract fun rocketDao(): RocketDao
    abstract fun favoriteRocketDao(): FavoriteRocketDao
}