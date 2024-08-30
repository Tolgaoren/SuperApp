package com.toren.data.repository

import android.util.Log
import com.toren.data.local.dao.FavoriteRocketDao
import com.toren.data.local.dao.RocketDao
import com.toren.data.local.entity.FavoriteRocketEntity
import com.toren.data.local.entity.toRocket
import com.toren.data.local.entity.toRocketEntity
import com.toren.data.remote.api.RocketApi
import com.toren.data.remote.dto.toRocket
import com.toren.domain.model.rocket.Rocket
import com.toren.domain.repository.RocketRepository
import javax.inject.Inject

class RocketRepositoryImpl
@Inject constructor(
    private val rocketDao: RocketDao,
    private val favoriteRocketDao: FavoriteRocketDao,
    private val api: RocketApi
) : RocketRepository {
    override suspend fun insertRocket(rocket: Rocket): Long {
        return rocketDao.insertRocket(rocket.toRocketEntity())
    }

    override suspend fun getRockets(): List<Rocket> {
        val localRockets = rocketDao.getRockets().map { it.toRocket() }
        if (localRockets.isEmpty()) {
            return refreshDb()
        }
        return localRockets
    }

    override suspend fun getRocket(id: String): Rocket {
        return rocketDao.getRocket(id).toRocket()
    }

    override suspend fun deleteRocket(id: Int): Int {
        return rocketDao.deleteRocket(id)
    }

    override suspend fun insertFavoriteRocket(rocketId: String): Long {
        return favoriteRocketDao.insertFavoriteRocket(
            FavoriteRocketEntity(
                rocketId = rocketId
            )
        )
    }

    override suspend fun getFavoriteRockets(): List<Rocket> {
        val ids = favoriteRocketDao.getFavoriteRockets().map { it.rocketId }
        Log.d("GetFavoriteRockets", "getFavoriteRockets: impl $ids")
        return rocketDao.getFavoriteRockets(ids)?.map { it.toRocket() } ?: emptyList()
    }

    override suspend fun isFavoriteRocket(id: String): Boolean {
        return favoriteRocketDao.isFavoriteRocket(id).rocketId.isNotEmpty()
    }

    override suspend fun deleteFavoriteRocket(id: String): Int {
        return favoriteRocketDao.deleteFavoriteRocket(id)
    }

    override suspend fun refreshDb(): List<Rocket> {
        val apiRockets = api.getLaunches().map { it.toRocket() }
        rocketDao.insertAllRockets(apiRockets.map { it.toRocketEntity() })
        return apiRockets
    }


}

