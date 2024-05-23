package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompositeWeatherTopAppBar(modifier: Modifier) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = { Text("Weather Forecast") },
        actions = {
            IconButton(onClick = { /* handle menu click */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Settings")
            }
        }
    )
}