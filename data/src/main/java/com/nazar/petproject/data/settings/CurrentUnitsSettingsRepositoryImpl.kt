package com.nazar.petproject.data.settings

import com.nazar.petproject.data.settings.data_sources.CurrentSettingsPreferencesDataStore
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentUnitsSettingsRepositoryImpl @Inject constructor(
    private val currentSettingsPreferencesDataStore: CurrentSettingsPreferencesDataStore,
    private val dispatcher: CoroutineDispatcher,
) : CurrentUnitsSettingsRepository {

    override fun getCurrentUnitForTemperature(): Flow<UnitFor.Temperature> {
        return currentSettingsPreferencesDataStore
            .temperatureUnitFlow
            .flowOn(dispatcher)
    }

    override suspend fun saveCurrentUnitOfTemperature(measurementUnit: UnitFor.Temperature) {
        withContext(dispatcher) {
            currentSettingsPreferencesDataStore.saveTemperature(measurementUnit)
        }
    }

    override fun getCurrentUnitForWindSpeed(): Flow<UnitFor.WindSpeed> {
        return currentSettingsPreferencesDataStore
            .windSpeedUnitFlow
            .flowOn(dispatcher)
    }

    override suspend fun saveCurrentUnitOfWindSpeed(measurementUnit: UnitFor.WindSpeed) {
        withContext(dispatcher) {
            currentSettingsPreferencesDataStore.saveWindSpeed(measurementUnit)
        }
    }
}