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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRockets(rockets: List<RocketEntity>) : List<Long>

    @Query("SELECT * FROM rockets")
    suspend fun getRockets(): List<RocketEntity>

    @Query("SELECT * FROM rockets WHERE rocket_id = :id")
    suspend fun getRocket(id: String): RocketEntity

    @Query("DELETE FROM rockets WHERE id = :id")
    suspend fun deleteRocket(id: Int) : Int

    @Query("SELECT * FROM rockets WHERE rocket_id IN (:rocketId)")
    suspend fun getFavoriteRockets(rocketId: List<String> = listOf()): List<RocketEntity>?
}