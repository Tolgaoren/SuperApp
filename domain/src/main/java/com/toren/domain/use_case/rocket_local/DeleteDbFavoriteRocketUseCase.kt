package com.toren.domain.use_case.rocket_local

import com.toren.domain.Resource
import com.toren.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteDbFavoriteRocketUseCase @Inject constructor(
    private val repository: RocketRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Int>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.deleteFavoriteRocket(id)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}