package com.toren.data.repository

import com.toren.data.local.dao.RocketDao
import com.toren.data.local.entity.toRocket
import com.toren.data.local.entity.toRocketEntity
import com.toren.domain.model.rocket.Rocket
import com.toren.domain.repository.RocketRepository
import javax.inject.Inject

class RocketRepositoryImpl
@Inject constructor(
    private val rocketDao: RocketDao,
) : RocketRepository {
    override suspend fun insertRocket(rocket: Rocket): Long {
        return rocketDao.insertRocket(rocket.toRocketEntity())
    }

    override suspend fun getRockets(): List<Rocket> {
        return rocketDao.getRockets().map { it.toRocket()}
    }

    override suspend fun deleteRocket(id: Int): Int {
        return rocketDao.deleteRocket(id)
    }
}

