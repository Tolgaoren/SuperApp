package com.toren.features_alarm.manager

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.toren.features_alarm.R

class AlarmNotificationManager(
    private val context: Context
) {
    companion object {
        const val CHANNEL_ID = "alarm_notification_channel_id"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val name = "Alarm Notifications"
            val descriptionText = "Notifications for Alarms"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    @SuppressLint("MissingPermission")
    fun sendNotification(alarmMessage: String) {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Alarm")
            .setContentText(alarmMessage)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0, notificationBuilder.build())
    }
}