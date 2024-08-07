package com.toren.domain.repository

import com.toren.domain.model.rocket_api.Rocket

interface RocketRepository {
    suspend fun getLaunches(): List<Rocket>
}