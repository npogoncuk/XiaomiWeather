package com.nazar.petproject.xiaomiweather.di

import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.data_sources.ApiDataSource
import com.nazar.petproject.domain.weather.AllWeatherUseCase
import com.nazar.petproject.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class WeatherModule {

    @Provides
    fun provideWeatherUseCase(weatherRepository: WeatherRepository): AllWeatherUseCase =
        AllWeatherUseCase.Base(weatherRepository)
}