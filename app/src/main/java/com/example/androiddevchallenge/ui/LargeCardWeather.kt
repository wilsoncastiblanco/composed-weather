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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.model.ScheduledWeather
import com.example.androiddevchallenge.ui.model.Weather

@Composable
fun LargeCardWeather(
    modifier: Modifier = Modifier,
    scheduledWeather: ScheduledWeather
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Card(
            modifier = modifier.padding(16.dp),
            elevation = 8.dp,
            backgroundColor = scheduledWeather.weather.color,
            shape = RoundedCornerShape(40.dp)
        ) {
            Wall(modifier)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.semantics {
                    disabled()
                }
            ) {
                WeatherVector(scheduledWeather, scheduledWeather.screen.title)
                WeatherData(scheduledWeather)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun WeatherData(scheduledWeather: ScheduledWeather) {
    val weather = scheduledWeather.weather
    WeatherTemperature(weather, scheduledWeather)
    Text(
        text = weather.weatherDescriptor.descriptor,
        style = MaterialTheme.typography.h1,
        color = Color.White,
        fontSize = 64.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.semantics {
            this.disabled()
        }
    )
    WindVelocity(scheduledWeather)
}

@Composable
private fun WeatherTemperature(
    weather: Weather,
    scheduledWeather: ScheduledWeather
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.semantics(mergeDescendants = true) {}
    ) {
        Text(
            text = weather.temperature.current,
            style = MaterialTheme.typography.h1,
            color = Color.White,
            fontSize = 164.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.semantics {
                contentDescription =
                    "The temperature ${scheduledWeather.screen.title} is ${weather.temperature.current}."
            }
        )
        Column {
            TemperatureLimit(
                temperature = weather.temperature.high,
                contentDescription = "High Temperature",
                vectorResource = R.drawable.ic_temp_high
            )
            TemperatureLimit(
                temperature = weather.temperature.low,
                contentDescription = "Low Temperature",
                vectorResource = R.drawable.ic_temp_low
            )
        }
    }
}

@Composable
private fun WindVelocity(scheduledWeather: ScheduledWeather) {
    val weather = scheduledWeather.weather
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.semantics(mergeDescendants = true) {}
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_wind),
            contentDescription = null,
            modifier = Modifier.size(54.dp),
            tint = Color.White
        )
        Text(
            text = weather.wind,
            style = MaterialTheme.typography.caption,
            color = Color.White,
            fontSize = 34.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.semantics {
                this.contentDescription =
                    "The Wind Speed ${scheduledWeather.screen.title} is ${weather.wind}"
            }
        )
    }
}

@Composable
private fun TemperatureLimit(
    temperature: String,
    contentDescription: String,
    vectorResource: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.semantics(mergeDescendants = true) {}
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = vectorResource),
            contentDescription = null,
            modifier = Modifier.size(54.dp),
            tint = Color.White
        )
        Text(
            text = temperature,
            style = MaterialTheme.typography.caption,
            color = Color.White,
            fontSize = 54.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.semantics {
                this.contentDescription = "$contentDescription $temperature"
            }
        )
    }
}
