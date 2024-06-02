package com.nazar.petproject.xiaomiweather.ui.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nazar.petproject.xiaomiweather.ui.screens.settings.components.HorizontalLineSpacer
import com.nazar.petproject.xiaomiweather.ui.screens.settings.components.SettingMediumTopAppBar
import com.nazar.petproject.xiaomiweather.ui.screens.settings.components.SettingsItem
import com.nazar.petproject.xiaomiweather.ui.screens.settings.components.SettingsSwitchItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            SettingMediumTopAppBar(
                modifier = Modifier,
                onBackClick = { navController.popBackStack() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->

        SettingsContent(modifier = Modifier.padding(paddingValues))

    }
}

@Composable
fun SettingsContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SettingsSection(title = "UNITS") {
            SettingsItem(
                title = "Temperature units",
                subtitle = "°C",
                onClick = { /* Handle temperature units click */ }
            )
            SettingsItem(
                title = "Wind speed units",
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
fun SettingsContentPreview() {
    SettingsContent()
}