package com.toren.features_rocket.ui.screens.rocket_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.domain.repository.RocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel
    @Inject constructor(
        private val repository: RocketRepository
    ): ViewModel() {


    fun test() {
        viewModelScope.launch {
            val data = repository.getLaunches()
            Log.d("data", data.toString())
        }
    }

}