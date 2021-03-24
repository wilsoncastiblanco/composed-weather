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
package com.example.androiddevchallenge.ui.weather

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androiddevchallenge.ui.AnimatedWeatherVector
import com.example.androiddevchallenge.ui.model.WeatherDescriptor

@Composable
fun Sunny(
    modifier: Modifier,
    weatherDescriptor: WeatherDescriptor
) {
    val infiniteRotation = rememberInfiniteTransition()

    val rotationRadius = infiniteRotation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing)
        )
    )

    AnimatedWeatherVector(
        weatherDescriptor = weatherDescriptor,
        modifier = modifier,
        secondaryAnimationEnabled = true,
        rotationRadius = rotationRadius.value,
        calculateCenterOffset = {
            weatherDescriptor.paths.find { it.name == weatherDescriptor.mainPath }!!.path.getBounds().center
        }
    )
}
