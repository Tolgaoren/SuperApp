package com.toren.domain.repository

import com.toren.domain.model.rocket.Rocket
import com.toren.domain.model.rocket.favorite_rocket.FavoriteRocket

interface RocketRepository {
    suspend fun insertRocket(rocket: Rocket): Long

    suspend fun getRockets(): List<Rocket>

    suspend fun deleteRocket(id: Int): Int
}