package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nazar.petproject.xiaomiweather.ui.Dimensions

@Composable
fun WindInfoBlock(
    windSpeed: Int,
    windSpeedUnit: String,
    windDirection: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(Dimensions.DEFAULT_ROUNDED_CORNER),
        color = Color(0xFF5C9DFF),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Min)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "$windSpeed$windSpeedUnit",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Compass(windDirectionAngle = windDirection.toFloat())
            }
        }
    }
}



@Composable
fun Compass(windDirectionAngle: Float) {
    Canvas(
        modifier = Modifier
            .size(80.dp)
            .padding(start = 16.dp)
    ) {
        val compassRadius = size.minDimension / 2f
        drawCircle(
            color = Color.White,
            radius = compassRadius,
            style = Stroke(width = 2.dp.toPx())
        )
        val directions = listOf("E", "N", "W", "S")
        val directionLabelTextSize = 24f
        directions.forEachIndexed { index, direction ->
            val angle = index * 90f
            val x = compassRadius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = compassRadius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
            val defaultDelta = directionLabelTextSize + 5f
            val directionLabelX = when(index) {
                0, 2 -> center.x + x + (if (index == 0) -1 else 1) * defaultDelta
                1, 3 -> center.x
                else -> 0f
            }
            val directionLabelY = when(index) {
                0, 2 -> center.y
                1, 3 -> center.y + y + (if (index == 1) -1 else 1) * defaultDelta
                else -> 0f
            }
            drawContext.canvas.nativeCanvas.drawText(
                direction,
                directionLabelX,
                directionLabelY,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = directionLabelTextSize
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
        // different direction start angle, so we need to subtract 90 degrees
        rotate(degrees = windDirectionAngle - 90) {
            drawLine(
                color = Color.White,
                start = center,
                end = center.copy(x = center.x + compassRadius - 10),
                strokeWidth = 4.dp.toPx()
            )
        }
    }
}