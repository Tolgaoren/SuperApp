package com.toren.domain.repository

import com.toren.domain.model.rocket.Rocket

interface RocketApiRepository {
    suspend fun getLaunches(): List<Rocket>
}