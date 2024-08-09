package com.toren.domain.use_case.rocket_local

import com.toren.domain.Resource
import com.toren.domain.model.rocket.Rocket
import com.toren.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertDbRocketsUseCase @Inject constructor(
    private val repository: RocketRepository
) {
    operator fun invoke(rockets: List<Rocket>) : Flow<Resource<List<Long>>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.insertAllRockets(rockets)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}