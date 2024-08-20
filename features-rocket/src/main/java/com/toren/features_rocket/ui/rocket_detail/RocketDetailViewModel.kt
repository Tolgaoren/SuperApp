package com.toren.features_rocket.ui.rocket_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.use_case.rocket_local.DeleteDbFavoriteRocketUseCase
import com.toren.domain.use_case.rocket_local.GetDbIsFavoriteRocketUseCase
import com.toren.domain.use_case.rocket_local.GetDbRocketUseCase
import com.toren.domain.use_case.rocket_local.InsertDbFavoriteRocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RocketDetailViewModel @Inject constructor(
    private val getDbRocketUseCase: GetDbRocketUseCase,
    private val getDbIsFavoriteRocketUseCase: GetDbIsFavoriteRocketUseCase,
    private val insertFavoriteRocketUseCase: InsertDbFavoriteRocketUseCase,
    private val deleteFavoriteRocketUseCase: DeleteDbFavoriteRocketUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(RocketDetailUiState())
    val uiState: StateFlow<RocketDetailUiState> = _uiState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun onEvent(event: RocketDetailUiEvent) {
        when (event) {
            is RocketDetailUiEvent.FavoriteRocket -> {
                if (isFavorite.value) {
                    deleteFavoriteRocket(event.rocketId)
                } else {
                    insertFavoriteRocket(event.rocketId)
                }
            }
        }
    }

    fun getRocketDetail(id: String) {
        getDbRocketUseCase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = RocketDetailUiState(isLoading = true)
                    Log.d("GetRocketDetail", "Loading rocket detail...")
                }

                is Resource.Success -> {
                    _uiState.value = RocketDetailUiState(rocket = result.data)
                    Log.d("GetRocketDetail", "Rocket detail loaded: ${result.data}")
                }

                is Resource.Error -> {
                    _uiState.value = RocketDetailUiState(error = result.message ?: "Error")
                    Log.e("GetRocketDetail", "Error loading rocket detail: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFavoriteStatus(id: String) {
        getDbIsFavoriteRocketUseCase(id).onEach {  result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("GetFavoriteStatus", "Loading favorite status...")
                }

                is Resource.Success -> {
                    _isFavorite.value = result.data ?: false
                    Log.d("GetFavoriteStatus", "Favorite status: ${isFavorite.value}")
                }

                is Resource.Error -> {
                    Log.e("GetFavoriteStatus", "Error loading favorite status: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun insertFavoriteRocket(rocketId: String) {
        insertFavoriteRocketUseCase(rocketId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("InsertFavoriteRocket", "Inserting favorite rocket...")
                }

                is Resource.Success -> {
                    _isFavorite.value = true
                    Log.d(
                        "InsertFavoriteRocket",
                        "Favorite rocket inserted with ID: ${result.data}"
                    )
                }

                is Resource.Error -> {
                    Log.e(
                        "InsertFavoriteRocket",
                        "Error inserting favorite rocket: ${result.message}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteFavoriteRocket(rocketId: String) {
        deleteFavoriteRocketUseCase(rocketId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("DeleteFavoriteRocket", "Deleting favorite rocket...")
                }

                is Resource.Success -> {
                    _isFavorite.value = false
                    Log.d("DeleteFavoriteRocket",
                        "${result.data} favorite rocket deleted. ID: $rocketId")
                }

                is Resource.Error -> {
                    Log.e(
                        "DeleteFavoriteRocket",
                        "Error deleting favorite rocket: ${result.message}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}