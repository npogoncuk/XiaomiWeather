package com.nazar.petproject.xiaomiweather.ui.screens.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.xiaomiweather.R
import com.nazar.petproject.xiaomiweather.ui.screens.settings.SettingsIntent
import com.nazar.petproject.xiaomiweather.ui.screens.settings.SettingsScreenState
import com.nazar.petproject.xiaomiweather.ui.screens.settings.subtitle

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    settingState: SettingsScreenState,
    onIntent: (SettingsIntent) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SettingsSection(title = "UNITS") {
            SettingsItemWithDropdown(
                title = stringResource(id = R.string.temprerature_units),
                subtitle = stringResource(id = settingState.currentTemperatureUnit.subtitle),
                options = settingState.temperatureUnits,
                selectedOption = settingState.currentTemperatureUnit,
                onOptionSelected = { selectedOption: MeasurementUnit ->
                    onIntent(SettingsIntent.ChangeUnit(selectedOption))
                }
            )
            SettingsItem(
                title = stringResource(id = R.string.wind_speed_units),
                subtitle = "Kilometers per hour (km/h)",
                onClick = { /* Handle wind speed units click */ }
            )
            SettingsItem(
                title = "Atmospheric pressure units",
                subtitle = "Millimeter of mercury (mmHg)",
                onClick = { /* Handle pressure units click */ },
            )
        }
        HorizontalLineSpacer(modifier = Modifier.padding(vertical = 32.dp))
        SettingsSection(title = "OTHER SETTINGS") {
            SettingsSwitchItem(
                title = "Update at night automatically",
                subtitle = "Update weather info between 23:00 and 07:00",
                checked = false,
                onCheckedChange = { /* Handle switch change */ }
            )
            SettingsSwitchItem(
                title = "Sound effects",
                subtitle = "Accompany weather changes by sound effects",
                checked = true,
                onCheckedChange = { /* Handle switch change */ }
            )
        }
        HorizontalLineSpacer(modifier = Modifier.padding(vertical = 32.dp))
        SettingsSection(title = "ABOUT WEATHER") {
            SettingsItem(
                title = "Feedback",
                onClick = { /* Handle feedback click */ }
            )
            SettingsItem(
                title = "Privacy Policy",
                onClick = { /* Handle privacy policy click */ }
            )
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title,
            color = Color(0xFF6200EE),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        content()
    }
}

@Preview
@Composable
private fun SettingsContentPreview() {
    val settingState = SettingsScreenState()
    SettingsContent(
        settingState = settingState,
        onIntent = {}
    )
}