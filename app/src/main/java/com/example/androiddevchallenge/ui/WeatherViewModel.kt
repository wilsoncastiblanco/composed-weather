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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.ui.model.PathScaleScreen
import com.example.androiddevchallenge.ui.model.ScheduledWeather
import com.example.androiddevchallenge.ui.model.WeatherScreen
import com.example.androiddevchallenge.ui.model.scheduledWeather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val selectedWeatherScreen = MutableStateFlow<WeatherScreen>(WeatherScreen.Today)
    private val pathScaleScreen = MutableStateFlow<PathScaleScreen>(PathScaleScreen.Large)
    private val scheduledWeatherList = MutableStateFlow(scheduledWeather(pathScaleScreen.value.pathConfig))
    private val tabs: MutableList<WeatherScreen> = mutableListOf()

    private val _uiState = MutableStateFlow(WeatherScreenState())
    val uiState: StateFlow<WeatherScreenState> = _uiState

    init {
        viewModelScope.launch {
            updateScreenData(WeatherScreen.Today)
            tabs.addAll(
                scheduledWeatherList.value.groupBy { it.screen }.keys
            )
        }
    }

    fun onWeatherScreenSelected(weatherScreen: WeatherScreen) {
        selectedWeatherScreen.value = weatherScreen
        updateScreenData(weatherScreen)
    }

    private fun updateScreenData(weatherScreen: WeatherScreen) {
        viewModelScope.launch {
            when (weatherScreen) {
                is WeatherScreen.Today,
                is WeatherScreen.Tomorrow -> {
                    pathScaleScreen.value = PathScaleScreen.Large
                    val data = scheduledWeather(pathScaleScreen.value.pathConfig).find { it.screen == weatherScreen }!!
                    _uiState.value = WeatherScreenState(
                        selectedScreen = weatherScreen,
                        screenData = ScreenData.Single(data),
                        citySelected = data.weather.location.city,
                        tabs = tabs
                    )
                }
                is WeatherScreen.NextDays -> {
                    pathScaleScreen.value = PathScaleScreen.Small
                    val data = scheduledWeather(pathScaleScreen.value.pathConfig).filter { it.screen is WeatherScreen.NextDays }
                    _uiState.value = WeatherScreenState(
                        selectedScreen = weatherScreen,
                        screenData = ScreenData.Multiple(data),
                        citySelected = data.first().weather.location.city,
                        tabs = tabs
                    )
                }
                else -> Unit
            }
        }
    }

    data class WeatherScreenState(
        val selectedScreen: WeatherScreen = WeatherScreen.Today,
        val screenData: ScreenData = ScreenData.None,
        val citySelected: String = "No City Selected",
        val tabs: List<WeatherScreen> = emptyList()
    )

    sealed class ScreenData {
        object None : ScreenData()
        data class Single(val scheduledWeather: ScheduledWeather) : ScreenData()
        data class Multiple(val scheduledWeather: List<ScheduledWeather>) : ScreenData()
    }
}
