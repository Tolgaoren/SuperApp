package com.toren.data.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.toren.data.local.TypeConverter
import com.toren.data.local.dao.NoteDao
import com.toren.data.local.database.LocalDatabase
import com.toren.data.repository.AlarmRepositoryImpl
import com.toren.data.repository.FavoriteRocketRepositoryImpl
import com.toren.data.repository.NoteRepositoryImpl
import com.toren.domain.repository.AlarmRepository
import com.toren.domain.repository.FavoriteRocketRepository
import com.toren.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application, typeConverter: TypeConverter): LocalDatabase {
        return Room
            .databaseBuilder(app, LocalDatabase::class.java, "local_db")
            .addTypeConverter(typeConverter)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNoteRepository(db: LocalDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao())
    }

    @Provides
    fun provideAlarmRepository(db: LocalDatabase): AlarmRepository {
        return AlarmRepositoryImpl(db.alarmDao())
    }

    @Provides
    fun provideFavoriteRocketRepository(db: LocalDatabase): FavoriteRocketRepository {
        return FavoriteRocketRepositoryImpl(db.rocketDao())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}