package com.nazar.petproject.domain.location

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.entities.ILocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getCurrentLocation(): Flow<IResult<ILocation>>
}