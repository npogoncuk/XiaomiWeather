package com.nazar.petproject.data.di

import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.WeatherRepositoryImpl
import com.nazar.petproject.data.weather.data_sources.ApiDataSource
import com.nazar.petproject.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun provideWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    abstract fun provideWeatherDataSource(
        weatherDataSource: ApiDataSource
    ): WeatherDataSource
}