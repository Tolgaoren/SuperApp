package com.toren.features_rocket.ui.favorite_rockets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.rocket.Rocket
import com.toren.domain.use_case.rocket_local.DeleteDbFavoriteRocketUseCase
import com.toren.domain.use_case.rocket_local.GetDbFavoriteRocketsUseCase
import com.toren.domain.use_case.rocket_local.InsertDbFavoriteRocketUseCase
import com.toren.features_rocket.ui.rocket_list.RocketListUiEvent
import com.toren.features_rocket.ui.rocket_list.RocketListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRocketsViewModel @Inject constructor(
    private val getFavoriteRocketsUseCase: GetDbFavoriteRocketsUseCase,
    private val insertFavoriteRocketUseCase: InsertDbFavoriteRocketUseCase,
    private val deleteFavoriteRocketUseCase: DeleteDbFavoriteRocketUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RocketListUiState())
    val uiState: StateFlow<RocketListUiState> = _uiState

    fun onEvent(event: FavoriteRocketsUiEvent) {
        when (event) {
            is FavoriteRocketsUiEvent.FavoriteRocket -> {
                if (uiState.value.rockets.contains(event.rocket)) {
                    deleteFavoriteRocket(event.rocket)
                } else {
                    insertFavoriteRocket(event.rocket)
                }
            }
            is FavoriteRocketsUiEvent.Refresh -> getFavoriteRockets()
        }
    }

    private fun getFavoriteRockets() {
        getFavoriteRocketsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = RocketListUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value = RocketListUiState(rockets = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _uiState.value = RocketListUiState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun insertFavoriteRocket(rocket: Rocket) {
        insertFavoriteRocketUseCase(rocket.id.toString()).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("InsertFavoriteRocket", "Inserting favorite rocket...")
                }

                is Resource.Success -> {
                    _uiState.value = uiState.value.copy(
                        rockets = uiState.value.rockets.plus(rocket)
                    )
                    Log.d(
                        "InsertFavoriteRocket",
                        "Favorite rocket inserted with ID: ${result.data}"
                    )
                    Log.d("InsertFavoriteRocket", "Favorite rockets: ${uiState.value.rockets.size}")
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

    private fun deleteFavoriteRocket(rocket: Rocket) {
        deleteFavoriteRocketUseCase(rocket.id.toString()).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("DeleteFavoriteRocket", "Deleting favorite rocket...")
                }

                is Resource.Success -> {
                    _uiState.value = uiState.value.copy(
                        rockets = _uiState.value.rockets.minus(rocket)
                    )
                    Log.d("DeleteFavoriteRocket",
                        "${result.data} favorite rocket deleted. ID: ${rocket.id}")
                    Log.d("InsertFavoriteRocket", "Favorite rockets: ${uiState.value.rockets.size}")
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