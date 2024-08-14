package com.toren.domain.use_case.rocket_local

import android.util.Log
import com.toren.domain.Resource
import com.toren.domain.model.rocket.Rocket
import com.toren.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDbFavoriteRocketsUseCase @Inject constructor(
        private val repository: RocketRepository
    ) {
        operator fun invoke(): Flow<Resource<List<Rocket>>> = flow {
            emit(Resource.Loading())
            try {
                val rockets = repository.getFavoriteRockets()
                Log.d("GetFavoriteRockets", "getFavoriteRockets: use case $rockets")
                emit(Resource.Success(rockets))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }
}
