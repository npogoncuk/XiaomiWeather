package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nazar.petproject.domain.weather.entities.daily_weather.IOneDayWeather
import com.nazar.petproject.xiaomiweather.R
import com.nazar.petproject.xiaomiweather.ui.Dimensions

@Composable
fun FiveDayForecastBlock(
    modifier: Modifier = Modifier,
    dailyWeatherList: List<IOneDayWeather>
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xA1263238),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.DEFAULT_SMALL_PADDING)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.five_day_forecast),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = stringResource(id = R.string.more_details),
                    color = Color.Blue
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            dailyWeatherList.forEach { item ->
                FiveDayForecastItemRow(forecastItem = item)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* //TODO Handle click */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF263238))
            ) {
                Text(text = stringResource(id = R.string.five_day_forecast), color = Color.White)
            }
        }
    }
}

@Composable
fun FiveDayForecastItemRow(forecastItem: IOneDayWeather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Clear,//painter = painterResource(id = R.drawable.ic_weather_cloudy), // Replace with your weather icon
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = forecastItem.date.toString())
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = forecastItem.weatherCode.toString())
        }
        Text(text = "${forecastItem.temperatureMax}° / ${forecastItem.temperatureMin}°")
    }
}