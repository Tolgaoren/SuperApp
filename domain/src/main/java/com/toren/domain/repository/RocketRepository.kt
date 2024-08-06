package com.toren.domain.repository

import com.toren.domain.model.Rocket

interface RocketRepository {
    suspend fun getLaunches(): List<Rocket>
}