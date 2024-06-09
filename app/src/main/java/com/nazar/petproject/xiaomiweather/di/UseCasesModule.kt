package com.nazar.petproject.xiaomiweather.di

import com.nazar.petproject.domain.settings.repositories.AvailableUnitsSettingsRepository
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.settings.usecases.ChangeCurrentUnitUseCase
import com.nazar.petproject.domain.settings.usecases.CurrentTemperatureUnitUseCase
import com.nazar.petproject.domain.settings.usecases.CurrentWindSpeedUnitUseCase
import com.nazar.petproject.domain.settings.usecases.GetTemperatureUnitsUseCase
import com.nazar.petproject.domain.settings.usecases.GetWindSpeedUnitsUseCase
import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.CurrentWeatherUseCase
import com.nazar.petproject.domain.weather.DailyWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun provideCurrentWeatherUseCases(
        weatherRepository: WeatherRepository,
        currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
    ): CurrentWeatherUseCase =
        CurrentWeatherUseCase.Base(weatherRepository, currentUnitsSettingsRepository)

    @Singleton
    @Provides
    fun provideDailyUseCases(
        weatherRepository: WeatherRepository,
        currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
    ): DailyWeatherUseCase =
        DailyWeatherUseCase.Base(weatherRepository, currentUnitsSettingsRepository)

    @Provides
    @Singleton
    fun provideGetTemperatureUnitsUseCase(availableUnitsSettingsRepository: AvailableUnitsSettingsRepository): GetTemperatureUnitsUseCase =
        GetTemperatureUnitsUseCase.Base(availableUnitsSettingsRepository)

    @Provides
    @Singleton
    fun provideCurrentTemperatureUnitUseCase(currentUnitsSettingsRepository: CurrentUnitsSettingsRepository): CurrentTemperatureUnitUseCase =
        CurrentTemperatureUnitUseCase.Base(currentUnitsSettingsRepository)

    @Provides
    @Singleton
    fun provideChangeCurrentUnitUseCase(currentUnitsSettingsRepository: CurrentUnitsSettingsRepository): ChangeCurrentUnitUseCase =
        ChangeCurrentUnitUseCase.Base(currentUnitsSettingsRepository)

    @Provides
    @Singleton
    fun provideGetWindSpeedUnitsUseCase(availableUnitsSettingsRepository: AvailableUnitsSettingsRepository): GetWindSpeedUnitsUseCase =
        GetWindSpeedUnitsUseCase.Base(availableUnitsSettingsRepository)

    @Provides
    @Singleton
    fun provideCurrentWindSpeedUnitUseCase(currentUnitsSettingsRepository: CurrentUnitsSettingsRepository): CurrentWindSpeedUnitUseCase =
        CurrentWindSpeedUnitUseCase.Base(currentUnitsSettingsRepository)
}