package com.toren.data.repository

import com.toren.data.remote.api.RocketApi
import com.toren.data.remote.dto.toRocket
import com.toren.domain.model.rocket.Rocket
import com.toren.domain.repository.RocketApiRepository
import javax.inject.Inject

class RocketApiRepositoryImpl
@Inject constructor(
    private val api: RocketApi,
) : RocketApiRepository {

    override suspend fun getLaunches(): List<Rocket> {
        val dto = api.getLaunches()
        return dto.map { it.toRocket() }
    }
}