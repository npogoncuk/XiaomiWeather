package com.nazar.petproject.data.di

import com.nazar.petproject.data.location.LocationRepositoryImpl
import com.nazar.petproject.data.settings.CurrentUnitsSettingsRepositoryImpl
import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.WeatherRepositoryImpl
import com.nazar.petproject.data.weather.data_sources.WeatherApiDataSource
import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    internal abstract fun provideWeatherDataSource(
        weatherDataSource: WeatherApiDataSource
    ): WeatherDataSource


    @Binds
    @Singleton
    abstract fun provideCurrentUnitsSettingsRepository(
        currentUnitsSettingsRepositoryImpl: CurrentUnitsSettingsRepositoryImpl
    ): CurrentUnitsSettingsRepository

    @Binds
    @Singleton
    abstract fun provideLocationRepository(
        locationRepository: LocationRepositoryImpl
    ): LocationRepository
}