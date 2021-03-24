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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.model.ScheduledWeather
import com.example.androiddevchallenge.ui.model.WeatherScreen

@Composable
fun SmallCardWeather(
    modifier: Modifier = Modifier,
    scheduledWeather: ScheduledWeather
) {
    val weather = remember { scheduledWeather.weather }
    val day = remember { (scheduledWeather.screen as WeatherScreen.NextDays).day }
    Card(
        modifier = modifier
            .padding(8.dp)
            .height(280.dp),
        elevation = 8.dp,
        backgroundColor = weather.color,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = day,
                style = MaterialTheme.typography.h2,
                color = Color.White,
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
            )
            WeatherVector(scheduledWeather, day)
            Text(
                text = weather.temperature.current,
                style = MaterialTheme.typography.h1,
                color = Color.White,
                fontSize = 44.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.semantics {
                    this.contentDescription = "The temperature $day is ${weather.temperature.current}"
                }
            )
            Text(
                text = weather.weatherDescriptor.descriptor,
                style = MaterialTheme.typography.h1,
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.clearAndSetSemantics {
                    this.disabled()
                }
            )
        }
    }
}
