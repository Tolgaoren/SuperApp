package com.toren.data.di

import com.toren.data.BuildConfig
import com.toren.data.remote.api.RocketApi
import com.toren.data.repository.RocketApiRepositoryImpl
import com.toren.domain.repository.RocketApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RocketModule {

    @Provides
    @Singleton
    fun provideRocketApi(): RocketApi {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RocketApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRocketRepository(api: RocketApi): RocketApiRepository {
        return RocketApiRepositoryImpl(api)
    }


}