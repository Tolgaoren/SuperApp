package com.toren.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_rockets",
        indices = [Index(value = ["rocket_id"], unique = true)])
data class FavoriteRocketEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "rocket_id") val rocketId: String,
)