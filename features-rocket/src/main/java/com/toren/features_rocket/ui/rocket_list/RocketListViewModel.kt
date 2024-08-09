package com.toren.features_rocket.ui.rocket_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.model.rocket.Rocket
import com.toren.domain.use_case.rocket_api.GetLaunchesUseCase
import com.toren.domain.use_case.rocket_local.GetDbRocketsUseCase
import com.toren.domain.use_case.rocket_local.InsertDbFavoriteRocketUseCase
import com.toren.domain.use_case.rocket_local.InsertDbRocketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel
@Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase,
    private val insertAllRocketsUseCase: InsertDbRocketsUseCase,
    private val getLocalRocketsUseCase: GetDbRocketsUseCase,
    private val insertFavoriteRocketUseCase: InsertDbFavoriteRocketUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RocketListUiState())
    val uiState: StateFlow<RocketListUiState> = _uiState

    init {
        getLocalRockets()
    }

    fun onEvent(event: RocketListUiEvent) {
        when (event) {
            is RocketListUiEvent.Refresh -> {
                getRockets()
            }
            is RocketListUiEvent.FavoriteRocket -> {
                insertFavoriteRocket(event.rocketId)
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
                    Log.d("InsertAllRockets", "All rockets inserted with IDs: ${result.data}")
                }

                is Resource.Error -> {
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
                    _uiState.value = RocketListUiState(rockets = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _uiState.value = RocketListUiState(error = result.message ?: "Error")
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
                    Log.d("InsertFavoriteRocket", "Favorite rocket inserted with ID: ${result.data}")
                }

                is Resource.Error -> {
                    Log.e("InsertFavoriteRocket", "Error inserting favorite rocket: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }
}
