package com.toren.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toren.data.local.entity.FavoriteRocketEntity

@Dao
interface RocketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocket(rocket: FavoriteRocketEntity) : Long

    @Query("SELECT * FROM rockets")
    suspend fun getRockets(): List<FavoriteRocketEntity>

    @Query("DELETE FROM rockets WHERE id = :id")
    suspend fun deleteRocket(id: Int) : Int
}