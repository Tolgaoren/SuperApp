package com.toren.features_rocket.ui.rocket_detail

import com.toren.domain.model.rocket.Rocket

data class RocketDetailUiState(
    val rocket: Rocket? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)