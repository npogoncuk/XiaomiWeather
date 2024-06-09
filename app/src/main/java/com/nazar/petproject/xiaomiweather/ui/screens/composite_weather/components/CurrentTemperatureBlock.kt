package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nazar.petproject.xiaomiweather.R

@Composable
fun CurrentTemperatureBlock(
    modifier: Modifier = Modifier,
    currentTemperature: Int,
    highLow: String,
    temperatureUnit: String,
    aqi: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Cyan),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "$currentTemperature",
                fontSize = 96.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = temperatureUnit,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(
            text = highLow,
            fontSize = 18.sp,
        )
        Surface(
            shape = MaterialTheme.shapes.small,
            color = Color(0xFF4CAF50),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "${stringResource(id = R.string.aqi)} $aqi",
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@Preview
@Composable
private fun CurrentTemperatureBlockPreview() {
    CurrentTemperatureBlock(
        currentTemperature = 25,
        highLow = "25° / 15°",
        temperatureUnit = "°C",
        aqi = "25"
    )
}