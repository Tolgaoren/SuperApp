package com.toren.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.toren.domain.Resource
import com.toren.domain.use_case.alarm.ReverseAlarmUseCase
import com.toren.manager.AlarmNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var reverseAlarmUseCase: ReverseAlarmUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: ""
        val id = intent.getIntExtra("EXTRA_ID", 0)
        Log.d("AlarmReceiver", "onReceive: $message")

        val notificationManager = AlarmNotificationManager(context)
        notificationManager.sendNotification(message)

        reverseAlarmState(id)
    }

    private fun reverseAlarmState(id: Int) {
        reverseAlarmUseCase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("AlarmReceiver", "Loading... id: $id")
                }

                is Resource.Success -> {
                    Log.d("AlarmReceiver", "Success: ${result.data}")
                }

                is Resource.Error -> {
                    Log.d("AlarmReceiver", "Error: ${result.message}")
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}