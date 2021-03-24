/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.withTransform
import com.example.androiddevchallenge.ui.model.WeatherDescriptor

@Composable
fun AnimatedWeatherVector(
    modifier: Modifier,
    weatherDescriptor: WeatherDescriptor,
    secondaryAnimationEnabled: Boolean,
    rotationRadius: Float = 0f,
    calculateCenterOffset: () -> Offset = { Offset(0f, 0f) }
) {
    var progressOffset by remember { mutableStateOf(0f) }
    var progressSecondaryOffset by remember { mutableStateOf(0f) }
    val centerOffset by remember { mutableStateOf(calculateCenterOffset()) }

    LaunchedEffect(key1 = Unit) {
        if (secondaryAnimationEnabled) {
            animate(
                initialValue = 0f,
                targetValue = 0.7f,
                animationSpec = repeatable(
                    repeatMode = RepeatMode.Reverse,
                    iterations = 15,
                    animation = tween(durationMillis = 50, easing = LinearOutSlowInEasing)
                )
            ) { animationValue, _ -> progressSecondaryOffset = animationValue }
        }

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

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {

        weatherDescriptor.paths.forEach { weatherPath ->
            Canvas(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .drawColoredShadow(
                        color = weatherPath.color,
                        alpha = if (weatherPath.name == weatherDescriptor.mainPath) progressOffset else progressSecondaryOffset,
                        path = weatherPath.path,
                        centerOffset = centerOffset,
                        rotationRadius = rotationRadius
                    )
                    .height(this.maxHeight / 2)

            ) {
                withTransform(
                    {
                        rotate(rotationRadius * 360, centerOffset)
                    },
                    {
                        drawPath(
                            path = weatherPath.path,
                            color = weatherPath.color
                        )
                    }
                )
            }
        }
    }
}
