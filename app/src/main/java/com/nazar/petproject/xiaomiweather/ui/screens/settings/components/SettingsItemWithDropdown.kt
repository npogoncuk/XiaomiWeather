package com.nazar.petproject.xiaomiweather.ui.screens.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.entities.units.isSame
import com.nazar.petproject.xiaomiweather.ui.screens.settings.subtitle

@Composable
fun SettingsItemWithDropdown(
    title: String,
    subtitle: String,
    options: List<MeasurementUnit>,
    selectedOption: MeasurementUnit,
    onOptionSelected: (MeasurementUnit) -> Unit,
    backgroundColor: Color = Color.Transparent,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = { expanded = true })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1.0f),
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2,
                modifier = Modifier.padding(end = 4.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Forward",
                tint = Color.Gray
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
            ) {
                options.forEach { option ->
                    val isThisOptionSelected = option isSame selectedOption
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = stringResource(id = option.subtitle),
                                    color = if (isThisOptionSelected) Color.Blue else Color.Black,
                                    fontWeight = if (isThisOptionSelected) FontWeight.Bold else FontWeight.Normal
                                )
                                if (isThisOptionSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color.Blue
                                    )
                                }
                            }

                        }
                    )
                }
            }
        }


    }
}


@Preview
@Composable
private fun SettingsItemWithDropdownPreview() {
    SettingsItemWithDropdown(
        title = "Atmospheric pressure units",
        subtitle = "Millimeter of mercury (mmHg)",
        options = listOf(object : MeasurementUnit { override val unitFor = UnitFor.Temperature.Celsius}, object : MeasurementUnit { override val unitFor = UnitFor.Temperature.Celsius}),
        selectedOption = object : MeasurementUnit { override val unitFor = UnitFor.Temperature.Celsius},
        onOptionSelected = {  },
    )
}