package com.toren.domain.repository

import com.toren.domain.model.rocket.Rocket

interface RocketRepository {
    suspend fun insertRocket(rocket: Rocket): Long

    suspend fun insertAllRockets(rockets: List<Rocket>): List<Long>

    suspend fun getRockets(): List<Rocket>

    suspend fun getRocket(id: String): Rocket

    suspend fun deleteRocket(id: Int): Int

    suspend fun insertFavoriteRocket(rocketId: String): Long

    suspend fun getFavoriteRockets(): List<Rocket>

    suspend fun isFavoriteRocket(id: String): Boolean

    suspend fun deleteFavoriteRocket(id: String): Int
}