package com.toren.data.repository

import com.toren.data.remote.api.RocketApi
import com.toren.data.remote.dto.toRocket
import com.toren.domain.model.rocket_api.Rocket
import com.toren.domain.repository.RocketRepository
import javax.inject.Inject

class RocketRepositoryImpl
@Inject constructor(
    private val api: RocketApi,
) : RocketRepository {

    override suspend fun getLaunches(): List<Rocket> {
        val dto = api.getLaunches()
        return dto.map { it.toRocket() }
    }
}