package com.toren.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toren.data.local.entity.FavoriteRocketEntity

@Dao
interface FavoriteRocketDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteRocket(favoriteRocket: FavoriteRocketEntity) : Long

    @Query("SELECT * FROM favorite_rockets")
    suspend fun getFavoriteRockets(): List<FavoriteRocketEntity>

    @Query("DELETE FROM favorite_rockets WHERE rocket_id = :id")
    suspend fun deleteFavoriteRocket(id: String) : Int

    @Query("SELECT * FROM favorite_rockets WHERE rocket_id = :id")
    suspend fun isFavoriteRocket(id: String): FavoriteRocketEntity
}