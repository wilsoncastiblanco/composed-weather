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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.androiddevchallenge.ui.model.ScheduledWeather
import com.example.androiddevchallenge.ui.model.WeatherDescriptor
import com.example.androiddevchallenge.ui.weather.Rainbowy
import com.example.androiddevchallenge.ui.weather.Rainy
import com.example.androiddevchallenge.ui.weather.Snowy
import com.example.androiddevchallenge.ui.weather.Stormy
import com.example.androiddevchallenge.ui.weather.Sunny

@Composable
fun WeatherVector(
    scheduledWeather: ScheduledWeather,
    weatherDay: String
) {
    val weather = scheduledWeather.weather
    val contentDescriptor =
        "The Weather $weatherDay is ${weather.weatherDescriptor.descriptor}"
    when (weather.weatherDescriptor) {
        is WeatherDescriptor.Sunny -> {
            Sunny(
                weatherDescriptor = weather.weatherDescriptor,
                modifier = Modifier.semantics {
                    contentDescription = contentDescriptor
                }
            )
        }
        is WeatherDescriptor.Stormy -> {
            Stormy(
                weatherDescriptor = weather.weatherDescriptor,
                modifier = Modifier.semantics {
                    contentDescription = contentDescriptor
                }
            )
        }
        is WeatherDescriptor.Rainbowy -> {
            Rainbowy(
                weatherDescriptor = weather.weatherDescriptor,
                modifier = Modifier.semantics {
                    contentDescription = contentDescriptor
                }
            )
        }
        is WeatherDescriptor.Rainy -> {
            Rainy(
                weatherDescriptor = weather.weatherDescriptor,
                modifier = Modifier.semantics {
                    contentDescription = contentDescriptor
                }
            )
        }
        is WeatherDescriptor.Snowy -> {
            Snowy(
                weatherDescriptor = weather.weatherDescriptor,
                modifier = Modifier.semantics {
                    contentDescription = contentDescriptor
                }
            )
        }
    }
}
