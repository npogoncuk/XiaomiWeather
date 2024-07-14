package com.nazar.petproject.data.settings.data_sources

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nazar.petproject.domain.settings.entities.units.UnitFor
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
    val temperatureUnitFlow: Flow<UnitFor.Temperature> = context.dataStore.data
        .map { preferences ->
            val temperatureStr = preferences[temperatureUnit]
            val unitsList = UnitFor.Temperature.entries
            val measurementUnit = unitsList.find { it.toString() == temperatureStr } ?: unitsList.first()
            measurementUnit
        }

    suspend fun saveTemperature(unit: UnitFor.Temperature) {
        context.dataStore.edit { settings ->
            settings[temperatureUnit] = unit.toString()
        }
    }

    private val windSpeedUnit = stringPreferencesKey("wind_speed_unit")
    val windSpeedUnitFlow: Flow<UnitFor.WindSpeed> = context.dataStore.data
        .map { preferences ->
            val windSpeedStr = preferences[windSpeedUnit]
            val unitsList = UnitFor.WindSpeed.entries
            val measurementUnit = unitsList.find { it.toString() == windSpeedStr } ?: unitsList.first()
            measurementUnit
        }

    suspend fun saveWindSpeed(unit: UnitFor.WindSpeed) {
        context.dataStore.edit { settings ->
            settings[windSpeedUnit] = unit.toString()
        }
    }
}