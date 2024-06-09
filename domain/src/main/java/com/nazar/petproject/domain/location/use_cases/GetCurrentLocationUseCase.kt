package com.nazar.petproject.domain.location.use_cases

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.location.entities.ILocation
import kotlinx.coroutines.flow.Flow

interface GetCurrentLocationUseCase {

    operator fun invoke(): Flow<IResult<ILocation>>

    class Base(
        private val locationRepository: LocationRepository
    ) : GetCurrentLocationUseCase {

        override fun invoke(): Flow<IResult<ILocation>> = locationRepository.getCurrentLocation()
    }

}