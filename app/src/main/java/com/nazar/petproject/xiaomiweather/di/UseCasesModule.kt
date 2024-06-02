package com.nazar.petproject.xiaomiweather.di

import com.nazar.petproject.data.settings.AvailableUnitsSettingsRepositoryImpl
import com.nazar.petproject.data.weather.WeatherRepositoryImpl
import com.nazar.petproject.domain.settings.repositories.AvailableUnitsSettingsRepository
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.settings.usecases.CurrentTemperatureUnitUseCase
import com.nazar.petproject.domain.settings.usecases.GetTemperatureUnitsUseCase
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

    @Provides
    fun provideGetTemperatureUnitsUseCase(availableUnitsSettingsRepository: AvailableUnitsSettingsRepository): GetTemperatureUnitsUseCase
            = GetTemperatureUnitsUseCase.Base(availableUnitsSettingsRepository)

    @Provides
    fun provideCurrentTemperatureUnitUseCase(currentUnitsSettingsRepository: CurrentUnitsSettingsRepository): CurrentTemperatureUnitUseCase
            = CurrentTemperatureUnitUseCase.Base(currentUnitsSettingsRepository)
}