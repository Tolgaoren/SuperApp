package com.toren.domain.use_case.rocket_local

import com.toren.domain.Resource
import com.toren.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertDbFavoriteRocketUseCase @Inject constructor(
    private val repository: RocketRepository
) {
    operator fun invoke(rocketId: String): Flow<Resource<Long>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.insertFavoriteRocket(rocketId)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}