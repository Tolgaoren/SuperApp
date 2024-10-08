package com.toren.features_alarm.di

import android.content.Context
import com.toren.features_alarm.manager.AlarmNotificationManager
import com.toren.domain.repository.AlarmScheduler
import com.toren.features_alarm.repository.AlarmSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {

    @Provides
    @Singleton
    fun provideAlarmNotificationManager(
        @ApplicationContext context: Context
    ): AlarmNotificationManager {
        return AlarmNotificationManager(context)
    }


    @Provides
    @Singleton
    fun provideAlarmScheduler(
        @ApplicationContext context: Context
    ): AlarmScheduler {
        return AlarmSchedulerImpl(context)
    }
}
