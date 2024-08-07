package com.toren.features_rocket.ui.rocket_list

import com.toren.domain.model.rocket_api.Rocket

data class RocketListUiState(
    val rockets: List<Rocket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
