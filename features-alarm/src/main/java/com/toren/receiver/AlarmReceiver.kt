package com.toren.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.toren.manager.AlarmNotificationManager

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: return
        Log.d("AlarmReceiver", "onReceive: $message")

        val notificationManager = AlarmNotificationManager(context)
        notificationManager.sendNotification(message)

    }
}