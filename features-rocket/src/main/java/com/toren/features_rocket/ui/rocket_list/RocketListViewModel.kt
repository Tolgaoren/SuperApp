package com.toren.features_rocket.ui.rocket_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.Resource
import com.toren.domain.use_case.rocket_api.GetLaunchesUseCase
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(RocketListUiState())
    val uiState: StateFlow<RocketListUiState> = _uiState

    init {
        getLaunches()
    }

    fun onEvent(event: RocketListUiEvent) {
        when (event) {
            is RocketListUiEvent.Refresh -> {
                getLaunches()
            }
        }
    }

    private fun getLaunches() {
        getLaunchesUseCase().onEach { result ->
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
}
