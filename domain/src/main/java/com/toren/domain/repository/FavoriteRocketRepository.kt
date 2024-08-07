package com.toren.domain.repository

import com.toren.domain.model.favorite_rocket.FavoriteRocket

interface FavoriteRocketRepository {
    suspend fun insertRocket(rocket: FavoriteRocket): Long

    suspend fun getRockets(): List<FavoriteRocket>

    suspend fun deleteRocket(id: Int): Int
}