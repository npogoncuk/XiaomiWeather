package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeatherValues
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import com.nazar.petproject.xiaomiweather.ui.Dimensions

@Composable
fun ColumnWithAllWeatherBlocks(
    modifier: Modifier = Modifier,
    currentWeather: ICurrentWeather,
    dailyWeather: IDailyWeather,
) {
    Column(
        modifier = modifier
    ) {
        val temperatureUnit = currentWeather.propertyToUnitMap["temperature2m"] ?: ""
        val dailyWeatherToday = dailyWeather.values.oneDayWeatherList.first()


        CurrentTemperatureBlock(
            modifier = Modifier.padding(Dimensions.DEFAULT_SMALL_PADDING),
            currentTemperature = currentWeather.values.temperature,
            temperatureUnit = temperatureUnit,
            highLow = "High ${dailyWeatherToday.temperatureMax}$temperatureUnit / Low ${dailyWeatherToday.temperatureMin}$temperatureUnit",
            aqi = "??"
        )

        FiveDayForecastBlock(
            dailyWeatherList = dailyWeather.values.oneDayWeatherList,
            modifier = Modifier.padding(Dimensions.DEFAULT_SMALL_PADDING)
        )

        Row(
            modifier = Modifier
                .background(color = Color(0xFF86CDF0))
                .height(200.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                WindInfoBlock(
                    windSpeed = currentWeather.values.windSpeed10m.toInt(),
                    windSpeedUnit = currentWeather.getUnit(ICurrentWeatherValues::windSpeed10m).toString(),
                    windDirection = currentWeather.values.windDirection10m,
                    modifier = Modifier
                        .weight(1f)
                        .padding(Dimensions.DEFAULT_SMALL_PADDING)
                )
                SunriseSunsetInfoBlock(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(Dimensions.DEFAULT_SMALL_PADDING)
                )
                /*
                WindInfoBlock(
                    windSpeed = currentWeather.values.windSpeed10m.toInt(),
                    windSpeedUnit = currentWeather.getUnit(ICurrentWeatherValues::windSpeed10m).toString(),
                    windDirection = currentWeather.values.windDirection10m,
                    modifier = Modifier
                        .weight(1f)
                        .padding(Dimensions.DEFAULT_SMALL_PADDING)
                )*/
            }
            WeatherDetailsBlock(
                currentWeather = currentWeather,
                modifier = Modifier
                    .background(color = Color(0xFFFFCDF0))
                    .padding(Dimensions.DEFAULT_SMALL_PADDING)
                    .height(IntrinsicSize.Max)
            )
        }


        SunriseSunsetInfoBlock(
            Modifier
                .fillMaxWidth()
                .padding(Dimensions.DEFAULT_SMALL_PADDING)
        )
    }
}