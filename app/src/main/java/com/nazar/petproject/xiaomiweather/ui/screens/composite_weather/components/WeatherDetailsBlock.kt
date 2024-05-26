package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.xiaomiweather.R
import com.nazar.petproject.xiaomiweather.ui.Dimensions


@Composable
fun WeatherDetailsBlock(
    currentWeather: ICurrentWeather,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(Dimensions.DEFAULT_ROUNDED_CORNER),
        color = Color(0xFF263238),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val weatherValues = currentWeather.values
            WeatherDetailRow(
                label = stringResource(id = R.string.humidity),
                value = weatherValues.relativeHumidity2m.toString(),
                unit = currentWeather.getUnit(weatherValues::relativeHumidity2m).toString()
            )
            WeatherDetailRow(
                label = stringResource(id = R.string.chance_of_rain),
                value = weatherValues.relativeHumidity2m.toString(),
                unit = currentWeather.getUnit(weatherValues::relativeHumidity2m).toString()
            )
            WeatherDetailRow(
                label = stringResource(id = R.string.real_feel),
                value = weatherValues.apparentTemperature.toString(),
                unit = currentWeather.getUnit(weatherValues::apparentTemperature).toString()
            )
            WeatherDetailRow(
                label = stringResource(id = R.string.pressure),
                value = weatherValues.pressureMsl.toString(),
                unit = currentWeather.getUnit(weatherValues::pressureMsl).toString()
            )
        }
    }
}

@Composable
private fun WeatherDetailRow(label: String, value: String, unit: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.White, fontSize = 12.sp)
        Text(text = value + unit, color = Color.White, fontSize = 12.sp)
    }
}