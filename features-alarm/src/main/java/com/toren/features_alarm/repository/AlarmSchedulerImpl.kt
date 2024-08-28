package com.toren.features_alarm.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.toren.domain.model.alarm.Alarm
import com.toren.domain.repository.AlarmScheduler
import com.toren.features_alarm.receiver.AlarmReceiver
import com.toren.features_alarm.util.toDateLong

class AlarmSchedulerImpl(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: Alarm) {
        val alarmTime = alarm.time.toDateLong()
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", alarm.message)
            putExtra("EXTRA_TIME", alarmTime)
            putExtra("EXTRA_ID", alarm.id)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            PendingIntent.getBroadcast(
                context,
                alarm.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        ))
    }

    override fun cancel(id: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}