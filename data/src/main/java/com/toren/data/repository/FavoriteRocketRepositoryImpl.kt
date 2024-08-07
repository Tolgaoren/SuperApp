package com.toren.data.repository

import com.toren.data.local.dao.RocketDao
import com.toren.data.local.entity.toFavoriteRocket
import com.toren.data.local.entity.toFavoriteRocketEntity
import com.toren.domain.model.favorite_rocket.FavoriteRocket
import com.toren.domain.repository.FavoriteRocketRepository
import javax.inject.Inject

class FavoriteRocketRepositoryImpl
@Inject constructor(
    private val rocketDao: RocketDao,
) : FavoriteRocketRepository {
    override suspend fun insertRocket(rocket: FavoriteRocket): Long {
        return rocketDao.insertRocket(rocket.toFavoriteRocketEntity())
    }

    override suspend fun getRockets(): List<FavoriteRocket> {
        return rocketDao.getRockets().map { it.toFavoriteRocket() }
    }

    override suspend fun deleteRocket(id: Int): Int {
        return rocketDao.deleteRocket(id)
    }
}

