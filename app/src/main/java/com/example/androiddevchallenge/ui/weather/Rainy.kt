package com.example.androiddevchallenge.ui.weather

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.drawColoredShadow
import com.example.androiddevchallenge.ui.model.WeatherDescriptor

@Composable
fun Rainy(
    weatherDescriptor: WeatherDescriptor
) {
    var progressOffset by remember { mutableStateOf(0f) }
    var progressThunderOffset by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = Unit) {
        animate(
            initialValue = 0f,
            targetValue = 0.7f,
            animationSpec = repeatable(
                repeatMode = RepeatMode.Reverse,
                iterations = 15,
                animation = tween(durationMillis = 50, easing = LinearOutSlowInEasing)
            )
        ) { animationValue, _ -> progressThunderOffset = animationValue }

        animate(
            initialValue = 0f,
            targetValue = 0.8f,
            animationSpec = repeatable(
                repeatMode = RepeatMode.Reverse,
                iterations = 2,
                animation = tween(durationMillis = 400, easing = FastOutSlowInEasing)
            )
        ) { animationValue, _ -> progressOffset = animationValue }

    }

    var bottomOffset by remember { mutableStateOf(0f) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {

        weatherDescriptor.paths.forEach {
            if (it.name == "cloud") {
                bottomOffset = it.path.getBounds().bottom / 2
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .drawColoredShadow(
                        color = it.color,
                        alpha = if (it.name == "cloud") progressOffset else progressThunderOffset,
                        path = it.path,
                    )
                    .semantics {
                        contentDescription = weatherDescriptor.descriptor
                    }
                    .height(this.maxHeight / 2)

            ) {
                drawPath(
                    path = it.path,
                    color = it.color
                )
            }
        }
    }
}
