package com.toren.features_rocket.ui.rocket_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.rocket.Rocket
import com.toren.domain.use_case.rocket_api.GetLaunchesUseCase
import com.toren.domain.use_case.rocket_local.DeleteDbFavoriteRocketUseCase
import com.toren.domain.use_case.rocket_local.GetDbFavoriteRocketsUseCase
import com.toren.domain.use_case.rocket_local.GetDbRocketsUseCase
import com.toren.domain.use_case.rocket_local.InsertDbFavoriteRocketUseCase
import com.toren.domain.use_case.rocket_local.InsertDbRocketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel
@Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase,
    private val insertAllRocketsUseCase: InsertDbRocketsUseCase,
    private val getLocalRocketsUseCase: GetDbRocketsUseCase,
    private val insertFavoriteRocketUseCase: InsertDbFavoriteRocketUseCase,
    private val getFavoriteRocketsUseCase: GetDbFavoriteRocketsUseCase,
    private val deleteFavoriteRocketUseCase: DeleteDbFavoriteRocketUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RocketListUiState())
    val uiState: StateFlow<RocketListUiState> = _uiState

    private val _favoriteRocketIds = MutableStateFlow(setOf<String>())
    val favoriteRocketIds: StateFlow<Set<String>> = _favoriteRocketIds

    private val _isDataSavedLocally = MutableStateFlow(false)
    val isDataSavedLocally: StateFlow<Boolean> = _isDataSavedLocally

    init {
        getLocalRockets()
        getFavoriteRockets()
    }

    fun onEvent(event: RocketListUiEvent) {
        when (event) {
            is RocketListUiEvent.Refresh -> {
                getRockets()
            }

            is RocketListUiEvent.FavoriteRocket -> {
                if (favoriteRocketIds.value.contains(event.rocketId)) {
                    deleteFavoriteRocket(event.rocketId)
                } else {
                    insertFavoriteRocket(event.rocketId)
                }
            }

            is RocketListUiEvent.SaveLocal -> {
                insertAllRockets(uiState.value.rockets)
            }

            RocketListUiEvent.LoadFavorites -> {
                getFavoriteRockets()
            }
        }
    }

    private fun getRockets() {
        getLaunchesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = RocketListUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value = RocketListUiState(rockets = result.data ?: emptyList())
                    insertAllRockets(result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _uiState.value = RocketListUiState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun insertAllRockets(rockets: List<Rocket>) {
        insertAllRocketsUseCase(rockets).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("InsertAllRockets", "Inserting all rockets...")
                }

                is Resource.Success -> {
                    _isDataSavedLocally.value = true
                    Log.d("InsertAllRockets", "All rockets inserted with IDs: ${result.data}")
                }

                is Resource.Error -> {
                    _isDataSavedLocally.value = false
                    Log.e("InsertAllRockets", "Error inserting all rockets: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getLocalRockets() {
        getLocalRocketsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = RocketListUiState(isLoading = true)
                }

                is Resource.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        getRockets()
                        Log.d("GetLocalRockets", "Getting rockets from API...")
                    } else {
                        _uiState.value = RocketListUiState(rockets = result.data ?: emptyList())
                        _isDataSavedLocally.value = true
                        Log.d("GetLocalRockets", "Getting rockets from database...")
                    }
                }

                is Resource.Error -> {
                    _uiState.value = RocketListUiState(error = result.message ?: "Error")
                    _isDataSavedLocally.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFavoriteRockets() {
            getFavoriteRocketsUseCase().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("GetFavoriteRockets", "Loading favorite rockets...")
                    }

                    is Resource.Success -> {
                        _favoriteRocketIds.value =
                            result.data?.map { it.id ?: "" }?.toSet() ?: setOf()
                        Log.d("GetFavoriteRockets", "Favorite rockets: ${favoriteRocketIds.value}")
                    }

                    is Resource.Error -> {
                        Log.e("GetFavoriteRockets",
                            "Error getting favorite rockets: ${result.message}"
                        )
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
                    _favoriteRocketIds.value = _favoriteRocketIds.value.plus(rocketId)
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
                    _favoriteRocketIds.value = _favoriteRocketIds.value.minus(rocketId)
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


