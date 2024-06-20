package com.nazar.petproject.domain.weather.use_cases

import com.nazar.petproject.domain.weather.WeatherRepository

interface WeatherUseCasesError {
    data class WeatherRepositoryError(val exception: WeatherRepository.Exceptions) : WeatherUseCasesError
}