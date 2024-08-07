package com.toren.features_rocket.ui.rocket_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.model.favorite_rocket.FavoriteRocket
import com.toren.domain.repository.FavoriteRocketRepository
import com.toren.domain.repository.RocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel
    @Inject constructor(
        private val rocketRepository: RocketRepository,
        private val favoriteRocketRepository: FavoriteRocketRepository
    ): ViewModel() {


    fun test() {
        viewModelScope.launch {
            val data = rocketRepository.getLaunches()
            Log.d("data", data.toString())
            val data2 = favoriteRocketRepository.insertRocket(
                FavoriteRocket(
                    name = "test"
                )
            )
            Log.d("data2", data2.toString())
        }
    }

}