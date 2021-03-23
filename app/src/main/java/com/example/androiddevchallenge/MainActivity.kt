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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.ConfigurationCompat
import com.example.androiddevchallenge.ui.model.PathScale
import com.example.androiddevchallenge.ui.model.PathScaleScreen
import com.example.androiddevchallenge.ui.model.ScheduledWeather
import com.example.androiddevchallenge.ui.model.Weather
import com.example.androiddevchallenge.ui.model.WeatherDescriptor
import com.example.androiddevchallenge.ui.model.WeatherScreen
import com.example.androiddevchallenge.ui.model.scheduledWeather
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.weather.Rainbowy
import com.example.androiddevchallenge.ui.weather.Rainy
import com.example.androiddevchallenge.ui.weather.Snowy
import com.example.androiddevchallenge.ui.weather.Stormy
import com.example.androiddevchallenge.ui.weather.Sunny

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                JetpackWeatherHome()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JetpackWeatherHome() {
    var tabSelected by remember { mutableStateOf<WeatherScreen>(WeatherScreen.Today) }
    var pathScale by remember { mutableStateOf<PathScaleScreen>(PathScaleScreen.Large) }
    var citySelected by remember { mutableStateOf<String>("No City Selected") }

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = tabSelected.position,
                backgroundColor = MaterialTheme.colors.background
            ) {
                scheduledWeather(pathScale.pathConfig)
                    .groupBy {
                        it.screen
                    }
                    .keys.forEachIndexed { index, weatherScreen ->
                        val selected = index == tabSelected.position
                        Tab(
                            selected = selected,
                            modifier = Modifier.padding(16.dp),
                            onClick = { tabSelected = weatherScreen }
                        ) {
                            Text(
                                color = Color.Black,
                                text = weatherScreen.title.toUpperCase(
                                    ConfigurationCompat.getLocales(LocalConfiguration.current)[0]
                                )
                            )
                        }
                    }
            }
        }

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = citySelected,
                style = MaterialTheme.typography.h1,
                color = Color.DarkGray,
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
            when (tabSelected) {
                is WeatherScreen.NextDays -> {
                    pathScale = PathScaleScreen.Small
                    val nextDays = scheduledWeather(pathScale.pathConfig).filter { it.screen is WeatherScreen.NextDays }
                    NextDaysWeather(nextDays)
                }
                else -> {
                    pathScale = PathScaleScreen.Large
                    val scheduledWeather = scheduledWeather(pathScale.pathConfig).find { it.screen == tabSelected }!!.weather
                    citySelected = scheduledWeather.location.city
                    CardWeather(weather = scheduledWeather)
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NextDaysWeather(nextDays: List<ScheduledWeather>) {
    val nextDaysWeather = remember { nextDays }
    LazyVerticalGrid(cells = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(nextDaysWeather) { scheduledWeather ->
            WeatherItem(scheduledWeather, Modifier.fillMaxSize())
        }
    }
}

@Composable
fun WeatherItem(scheduledWeather: ScheduledWeather, modifier: Modifier = Modifier) {
    val weather = remember { scheduledWeather.weather }
    val day = remember { (scheduledWeather.screen as WeatherScreen.NextDays).day }

    Card(
        modifier = modifier.padding(8.dp).height(280.dp),
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
            WeatherDescriptor(weather)
            Text(
                text = weather.temperature.current,
                style = MaterialTheme.typography.h1,
                color = Color.White,
                fontSize = 44.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = weather.weatherDescriptor.descriptor,
                style = MaterialTheme.typography.h1,
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
            )
        }
    }

}

@Composable
private fun CardWeather(weather: Weather, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Card(
            modifier = modifier.padding(16.dp),
            elevation = 8.dp,
            backgroundColor = weather.color,
            shape = RoundedCornerShape(40.dp)
        ) {
            Wall(modifier)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WeatherDescriptor(weather)
                WeatherData(weather)
            }
        }

    }
}

@Composable
private fun WeatherData(weather: Weather) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = weather.temperature.current,
            style = MaterialTheme.typography.h1,
            color = Color.White,
            fontSize = 164.sp,
            textAlign = TextAlign.Center,
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
    Text(
        text = weather.weatherDescriptor.descriptor,
        style = MaterialTheme.typography.h1,
        color = Color.White,
        fontSize = 64.sp,
        textAlign = TextAlign.Center,
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_wind),
            contentDescription = "Wind Velocity",
            modifier = Modifier.size(54.dp),
            tint = Color.White
        )
        Text(
            text = weather.wind,
            style = MaterialTheme.typography.caption,
            color = Color.White,
            fontSize = 34.sp,
            textAlign = TextAlign.Center,
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = vectorResource),
            contentDescription = contentDescription,
            modifier = Modifier.size(54.dp),
            tint = Color.White
        )
        Text(
            text = temperature,
            style = MaterialTheme.typography.caption,
            color = Color.White,
            fontSize = 54.sp,
            textAlign = TextAlign.Center,
        )

    }

}

@Composable
private fun WeatherDescriptor(weather: Weather) {
    when (weather.weatherDescriptor) {
        is WeatherDescriptor.Sunny -> {
            Sunny(weatherDescriptor = weather.weatherDescriptor)
        }
        is WeatherDescriptor.Stormy -> {
            Stormy(weatherDescriptor = weather.weatherDescriptor)
        }
        is WeatherDescriptor.Rainbowy -> {
            Rainbowy(weatherDescriptor = weather.weatherDescriptor)
        }
        is WeatherDescriptor.Rainy -> {
            Rainy(weatherDescriptor = weather.weatherDescriptor)
        }
        is WeatherDescriptor.Snowy -> {
            Snowy(weatherDescriptor = weather.weatherDescriptor)
        }
    }
}


@Composable
private fun Wall(modifier: Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        (0..size.height.toInt()).filter { it % 42 == 0 }.forEach { height ->
            (0..size.width.toInt()).filter { it % 86 == 0 }.forEach { width ->
                val offsetWidth =
                    if ((height / 42) % 2 == 0) width.toFloat() - 40f else width.toFloat()
                drawRect(
                    color = Color(0xFF805A46),
                    topLeft = Offset(x = offsetWidth, y = height.toFloat()),
                    size = Size(width = 80f, height = 40f),
                    style = Stroke()
                )
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        JetpackWeatherHome()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        JetpackWeatherHome()
    }
}

