package com.nazar.petproject.data.settings.data_sources

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nazar.petproject.data.settings.units_list_storage.TemperatureUnits
import com.nazar.petproject.data.settings.units_list_storage.WindUnits
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class CurrentSettingsPreferencesDataStore @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private val temperatureUnit = stringPreferencesKey("temperature_unit")
    val temperatureUnitFlow: Flow<MeasurementUnit> = context.dataStore.data
        .map { preferences ->
            val temperatureStr = preferences[temperatureUnit]
            val unitsList = TemperatureUnits.units
            val measurementUnit = unitsList.find { it.toString() == temperatureStr } ?: unitsList.first()
            measurementUnit
        }

    suspend fun saveTemperature(unit: MeasurementUnit) {
        context.dataStore.edit { settings ->
            settings[temperatureUnit] = unit.toString()
        }
    }

    private val windSpeedUnit = stringPreferencesKey("wind_speed_unit")
    val windSpeedUnitFlow: Flow<MeasurementUnit> = context.dataStore.data
        .map { preferences ->
            val windSpeedStr = preferences[windSpeedUnit]
            val unitsList = WindUnits.units
            val measurementUnit = unitsList.find { it.toString() == windSpeedStr } ?: unitsList.first()
            measurementUnit
        }

    suspend fun saveWindSpeed(unit: MeasurementUnit) {
        context.dataStore.edit { settings ->
            settings[windSpeedUnit] = unit.toString()
        }
    }
}