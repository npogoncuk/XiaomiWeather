package com.nazar.petproject.xiaomiweather.di

import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.location.use_cases.GetCurrentLocationUseCase
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.settings.usecases.ChangeCurrentUnitUseCase
import com.nazar.petproject.domain.settings.usecases.CurrentTemperatureUnitUseCase
import com.nazar.petproject.domain.settings.usecases.CurrentWindSpeedUnitUseCase
import com.nazar.petproject.domain.settings.usecases.GetTemperatureUnitsUseCase
import com.nazar.petproject.domain.settings.usecases.GetWindSpeedUnitsUseCase
import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.use_cases.CurrentWeatherUseCase
import com.nazar.petproject.domain.weather.use_cases.DailyWeatherUseCase
import com.nazar.petproject.domain.weather.use_cases.GetAllWeatherDataUseCase
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
        locationRepository: LocationRepository,
    ): CurrentWeatherUseCase =
        CurrentWeatherUseCase.Base(weatherRepository, currentUnitsSettingsRepository, locationRepository)

    @Singleton
    @Provides
    fun provideDailyUseCases(
        weatherRepository: WeatherRepository,
        currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
        locationRepository: LocationRepository
    ): DailyWeatherUseCase =
        DailyWeatherUseCase.Base(weatherRepository, currentUnitsSettingsRepository, locationRepository)

    @Provides
    @Singleton
    fun provideGetTemperatureUnitsUseCase(): GetTemperatureUnitsUseCase =
        GetTemperatureUnitsUseCase.Base()

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
    fun provideGetWindSpeedUnitsUseCase(): GetWindSpeedUnitsUseCase =
        GetWindSpeedUnitsUseCase.Base()

    @Provides
    @Singleton
    fun provideCurrentWindSpeedUnitUseCase(currentUnitsSettingsRepository: CurrentUnitsSettingsRepository): CurrentWindSpeedUnitUseCase =
        CurrentWindSpeedUnitUseCase.Base(currentUnitsSettingsRepository)

    @Provides
    @Singleton
    fun provideCurrentLocationUseCase(locationRepository: LocationRepository): GetCurrentLocationUseCase =
        GetCurrentLocationUseCase.Base(locationRepository)

    @Provides
    @Singleton
    fun provideAllWeatherUseCase(
        weatherRepository: WeatherRepository,
        currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
        locationRepository: LocationRepository,
    ): GetAllWeatherDataUseCase =
        GetAllWeatherDataUseCase.Base(weatherRepository, currentUnitsSettingsRepository, locationRepository)

}