package com.nazar.petproject.data.location

import com.nazar.petproject.data.location.data_sources.FusedLocationDataSource
import com.nazar.petproject.domain.DomainException
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

    override fun getCurrentLocation(): Flow<IResult<ILocation>> = flow {
        try {
            emit(IResult.Success(fusedLocationDataSource.getCurrentLocation()))
        } catch (e: Exception) {
            emit(IResult.Error(object : DomainException(e.message ?: "Unknown error") {}))
        }
    }.flowOn(dispatcher)
}