package com.nazar.petproject.data.location

import android.util.Log
import com.nazar.petproject.data.location.data_sources.FusedLocationDataSource
import com.nazar.petproject.data.location.data_sources.LocationPermissionNotGrantedException
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.location.entities.ILocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val fusedLocationDataSource: FusedLocationDataSource,
    private val dispatcher: CoroutineDispatcher,
) : LocationRepository {

    override fun getCurrentLocation(): Flow<IResult<ILocation, LocationRepository.Exceptions>> = flow {
        try {
            emit(IResult.Success(fusedLocationDataSource.getCurrentLocation()).also {
                Log.d("LocationRepositoryImpl", "getCurrentLocation: $it")
            })
        } catch (e: Exception) {
            emit(IResult.Error(
                when (e) {
                    is LocationPermissionNotGrantedException -> LocationRepository.Exceptions.PermissionRequiredException(e)
                    else -> LocationRepository.Exceptions.UnknownException(e)
                })
            )
        }
    }.flowOn(dispatcher)
}