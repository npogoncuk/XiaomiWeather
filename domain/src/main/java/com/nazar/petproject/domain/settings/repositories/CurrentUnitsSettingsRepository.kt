package com.nazar.petproject.domain.settings.repositories

import com.nazar.petproject.domain.settings.entities.units.UnitFor
import kotlinx.coroutines.flow.Flow

interface CurrentUnitsSettingsRepository {

    fun getCurrentUnitForTemperature(): Flow<UnitFor.Temperature>
    suspend fun saveCurrentUnitOfTemperature(measurementUnit: UnitFor.Temperature)

    fun getCurrentUnitForWindSpeed(): Flow<UnitFor.WindSpeed>
    suspend fun saveCurrentUnitOfWindSpeed(measurementUnit: UnitFor.WindSpeed)
}