package com.toren.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toren.data.local.entity.RocketEntity

@Dao
interface RocketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocket(rocket: RocketEntity) : Long

    @Query("SELECT * FROM rockets")
    suspend fun getRockets(): List<RocketEntity>

    @Query("DELETE FROM rockets WHERE id = :id")
    suspend fun deleteRocket(id: Int) : Int
}