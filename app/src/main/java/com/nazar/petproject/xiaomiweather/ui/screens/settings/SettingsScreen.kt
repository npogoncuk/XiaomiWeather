package com.nazar.petproject.xiaomiweather.ui.screens.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nazar.petproject.xiaomiweather.ui.screens.settings.components.SettingMediumTopAppBar
import com.nazar.petproject.xiaomiweather.ui.screens.settings.components.SettingsContent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingMediumTopAppBar(
                onBackClick = { navController.popBackStack() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        SettingsContent(modifier = Modifier.padding(paddingValues))
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}