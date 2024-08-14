package com.toren.domain.use_case.rocket_local

import com.toren.domain.Resource
import com.toren.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDbIsFavoriteRocketUseCase @Inject constructor(
    private val repository: RocketRepository
) {
    operator fun invoke(id: String) : Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            repository.isFavoriteRocket(id)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}