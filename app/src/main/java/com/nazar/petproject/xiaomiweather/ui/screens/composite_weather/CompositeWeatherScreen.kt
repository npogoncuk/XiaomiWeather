package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nazar.petproject.xiaomiweather.ui.Destination
import com.nazar.petproject.xiaomiweather.ui.Dimensions
import com.nazar.petproject.xiaomiweather.ui.Dimensions.DEFAULT_SMALL_PADDING

@Composable
fun CompositeWeatherScreen(
    navController: NavController,
    modifier: Modifier,
) {
    Scaffold(
        topBar = { CompositeWeatherTopAppBar() }
    ) { paddingValues ->

        val scrollState = rememberLazyListState()

        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            item {
                Text(
                    text = "25°C",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(DEFAULT_SMALL_PADDING)
                        .background(color = Color.Cyan),
                    fontSize = 48.sp
                )
            }
            item { CurrentTemperatureBlock(
                modifier = Modifier.padding(DEFAULT_SMALL_PADDING),
                currentTemperature = 25,
                temperatureUnit = "°C",
                highLow = "High 25°C / Low 25°C",
                aqi = "AQI 25"
            ) }
        }
    }
}