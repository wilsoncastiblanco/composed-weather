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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.LargeCardWeather
import com.example.androiddevchallenge.ui.SmallCardWeather
import com.example.androiddevchallenge.ui.WeatherTabs
import com.example.androiddevchallenge.ui.WeatherViewModel
import com.example.androiddevchallenge.ui.model.ScheduledWeather
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                val weatherViewModel = remember { WeatherViewModel() }
                JetpackWeatherHome(modifier = Modifier.fillMaxSize(), viewModel = weatherViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JetpackWeatherHome(
    modifier: Modifier,
    viewModel: WeatherViewModel
) {
    val viewState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            WeatherTabs(viewState, viewModel)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            City(viewState)
            Weather(viewState)
        }
    }
}

@Composable
private fun City(viewState: WeatherViewModel.WeatherScreenState) {
    Text(
        text = viewState.citySelected,
        style = MaterialTheme.typography.h1,
        color = Color.DarkGray,
        fontSize = 34.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(8.dp)
            .semantics {
                contentDescription =
                    "The City Selected ${viewState.selectedScreen.title} is ${viewState.citySelected}"
            },
    )
}

@Composable
private fun Weather(viewState: WeatherViewModel.WeatherScreenState) {
    when (viewState.screenData) {
        is WeatherViewModel.ScreenData.Single -> {
            val scheduledWeather = viewState.screenData.scheduledWeather
            LargeCardWeather(scheduledWeather = scheduledWeather)
        }
        is WeatherViewModel.ScreenData.Multiple -> {
            val nextDays = viewState.screenData.scheduledWeather
            NextDaysWeather(nextDays)
        }
        else -> Unit
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NextDaysWeather(nextDays: List<ScheduledWeather>) {
    val nextDaysWeather = remember { nextDays }
    LazyVerticalGrid(cells = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(nextDaysWeather) { scheduledWeather ->
            SmallCardWeather(scheduledWeather = scheduledWeather, modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        JetpackWeatherHome(modifier = Modifier, viewModel = WeatherViewModel())
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        JetpackWeatherHome(modifier = Modifier, viewModel = WeatherViewModel())
    }
}
