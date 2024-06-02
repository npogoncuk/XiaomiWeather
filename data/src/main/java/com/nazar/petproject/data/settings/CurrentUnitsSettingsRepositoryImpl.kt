package com.nazar.petproject.data.settings

import com.nazar.petproject.data.settings.data_sources.CurrentSettingsPreferencesDataStore
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
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

    override fun getCurrentUnitForTemperature(): Flow<MeasurementUnit> {
        return currentSettingsPreferencesDataStore
            .temperatureUnitFlow
            .flowOn(dispatcher)
    }

    override suspend fun saveCurrentUnitOfTemperature(measurementUnit: MeasurementUnit) {
        withContext(dispatcher) {
            currentSettingsPreferencesDataStore.saveTemperature(measurementUnit)
        }
    }

}