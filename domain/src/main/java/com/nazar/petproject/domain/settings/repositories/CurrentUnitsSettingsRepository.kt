package com.nazar.petproject.domain.settings.repositories

import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import kotlinx.coroutines.flow.Flow

interface CurrentUnitsSettingsRepository {

    fun getCurrentUnitForTemperature(): Flow<MeasurementUnit>
    suspend fun saveCurrentUnitOfTemperature(measurementUnit: MeasurementUnit)

    fun getCurrentUnitForWindSpeed(): Flow<MeasurementUnit>
    suspend fun saveCurrentUnitOfWindSpeed(measurementUnit: MeasurementUnit)
}