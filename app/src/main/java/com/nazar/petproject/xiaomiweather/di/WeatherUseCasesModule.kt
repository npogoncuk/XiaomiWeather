package com.nazar.petproject.xiaomiweather.di

import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class WeatherUseCasesModule {

    @Provides
    fun provideWeatherUseCases(weatherRepository: WeatherRepository): WeatherUseCase = WeatherUseCase.Base(weatherRepository)
}