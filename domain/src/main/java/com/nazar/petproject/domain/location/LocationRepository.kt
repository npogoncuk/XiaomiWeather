package com.nazar.petproject.domain.location

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.entities.ILocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getCurrentLocation(): Flow<IResult<ILocation, Exceptions>>

    sealed class Exceptions(
        val message: String? = null,
    ) {
        data class PermissionRequiredException(
            val exception: Throwable? = null,
        ) : Exceptions(exception?.message)

        data class StreetNullException(
            val exception: Throwable? = null,
        ) : Exceptions(exception?.message)

        data class UnknownException(
            val exception: Throwable,
        ) : Exceptions(exception.message)
    }
}