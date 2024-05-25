package com.nazar.petproject.xiaomiweather.di

import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.CurrentWeatherUseCase
import com.nazar.petproject.domain.weather.DailyWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun provideCurrentWeatherUseCases(weatherRepository: WeatherRepository): CurrentWeatherUseCase = CurrentWeatherUseCase.Base(weatherRepository)
    @Provides
    fun provideDailyUseCases(weatherRepository: WeatherRepository): DailyWeatherUseCase = DailyWeatherUseCase.Base(weatherRepository)
}