package com.nazar.petproject.domain.weather.use_cases

import com.nazar.petproject.domain.weather.WeatherRepository

interface WeatherUseCasesError {
    val message: String?

    data class WeatherRepositoryError(val exception: WeatherRepository.Exceptions) : WeatherUseCasesError {
        override val message: String?
            get() = exception.message
    }
}