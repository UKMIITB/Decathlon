package com.example.decathlon.di

import com.example.decathlon.common.DecathlonRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DecathlonModule {

    @Singleton
    @Provides
    fun providesRetrofitClient(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.decathlon.in/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesNaviPayApiService(retrofit: Retrofit): DecathlonRetrofitService =
        retrofit.create(DecathlonRetrofitService::class.java)
}