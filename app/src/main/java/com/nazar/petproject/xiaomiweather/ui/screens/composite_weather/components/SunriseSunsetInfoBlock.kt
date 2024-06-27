package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nazar.petproject.xiaomiweather.ui.Dimensions

@Composable
fun SunriseSunsetInfoBlock(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(Dimensions.DEFAULT_ROUNDED_CORNER),
        color = Color(0xFF5C9DFF),
        modifier = modifier
            .fillMaxWidth()
    ) {
        SunInfoCard(modifier, "06:00", "18:00")
    }
}

@Composable
fun SunInfoCard(
    modifier: Modifier = Modifier,
    sunriseTime: String,
    sunsetTime: String
) {
    Box(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Min)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "$sunriseTime Sunrise",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$sunsetTime Sunset",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            /*Box(modifier = Modifier,
                cente) {

            }*/
            SunPath(dotsX = 0.65f)
        }
    }
}

@Composable
fun SunPath(dotsX: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val path = Path().apply {
            moveTo(0f, size.height)
            quadraticBezierTo(
                size.width / 2, -size.height,
                size.width, size.height
            )
        }
        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = 2.dp.toPx())
        )
        drawCircle(
            color = Color.White,
            radius = 5.dp.toPx(),
            center = path.getPointOnPath(dotsX)
        )
    }
}

fun Path.getPointOnPath(fraction: Float): androidx.compose.ui.geometry.Offset {
    val measure = android.graphics.PathMeasure(this.asAndroidPath(), false)
    val pos = FloatArray(2)
    measure.getPosTan(measure.length * fraction, pos, null)
    return androidx.compose.ui.geometry.Offset(pos[0], pos[1])
}
