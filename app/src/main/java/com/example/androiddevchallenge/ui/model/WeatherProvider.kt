package com.example.androiddevchallenge.ui.model

import androidx.compose.ui.graphics.Color

sealed class WeatherDescriptor(val paths: List<WeatherPath>, val descriptor: String) {
    data class Stormy(val scale: PathScale) : WeatherDescriptor(stormy(scale), "Stormy")
    data class Sunny(val scale: PathScale) : WeatherDescriptor(sunny(scale), "Sunny")
    data class Rainy(val scale: PathScale) : WeatherDescriptor(rainy(scale), "Rainy")
    data class Rainbowy(val scale: PathScale) : WeatherDescriptor(rainbowy(scale), "RainbowY")
    data class Snowy(val scale: PathScale) : WeatherDescriptor(snowy(scale), "RainbowY")
}

data class Location(
    val city: String
)

data class Temperature(
    val current: String,
    val low: String,
    val high: String
)

data class Weather(
    val weatherDescriptor: WeatherDescriptor,
    val location: Location,
    val temperature: Temperature,
    val color: Color,
    val wind: String,
)

private fun todayWeather(scale: PathScale) = Weather(
    weatherDescriptor = WeatherDescriptor.Sunny(scale),
    location = Location(city = "Bogotá D.C"),
    temperature = Temperature(current = "21°", low = "16°", high = "22°"),
    //color = Color(0xFF6FC0FF),
    color = Color.DarkGray,
    wind = "7.5 km/h"
)

private fun tomorrowWeather(scale: PathScale) = Weather(
    weatherDescriptor = WeatherDescriptor.Stormy(scale),
    location = Location(city = "Bogotá D.C"),
    temperature = Temperature(current = "18°", low = "9°", high = "19°"),
    color = Color.DarkGray,
    wind = "11.3 km/h"
)

private fun dayThreeWeather(scale: PathScale) = Weather(
    weatherDescriptor = WeatherDescriptor.Snowy(scale),
    location = Location(city = "Bogotá D.C"),
    temperature = Temperature(current = "-1°", low = "-3°", high = "0°"),
    //color = Color.LightGray,
    color = Color.DarkGray,
    wind = "40.3 km/h"
)

private fun dayFourWeather(scale: PathScale) = Weather(
    weatherDescriptor = WeatherDescriptor.Sunny(scale),
    location = Location(city = "Bogotá D.C"),
    temperature = Temperature(current = "23°", low = "17°", high = "23°"),
    //color = Color(0xFF6FC0FF),
    color = Color.DarkGray,
    wind = "5.5 km/h"
)

private fun dayFiveWeather(scale: PathScale) = Weather(
    weatherDescriptor = WeatherDescriptor.Rainbowy(scale),
    location = Location(city = "Bogotá D.C"),
    temperature = Temperature(current = "17°", low = "12°", high = "18°"),
    //color = Color(0xFF6FC0FF),
    color = Color.DarkGray,
    wind = "15.5 km/h"
)

private fun daySixWeather(scale: PathScale) = Weather(
    weatherDescriptor = WeatherDescriptor.Stormy(scale),
    location = Location(city = "Bogotá D.C"),
    temperature = Temperature(current = "11°", low = "8°", high = "17°"),
    color = Color.DarkGray,
    wind = "11.3 km/h"
)

private fun daySevenWeather(scale: PathScale) = Weather(
    weatherDescriptor = WeatherDescriptor.Sunny(scale),
    location = Location(city = "Bogotá D.C"),
    temperature = Temperature(current = "18°", low = "11°", high = "19°"),
    color = Color.DarkGray,
    wind = "21.3 km/h"
)
private fun dayEightWeather(scale: PathScale) = Weather(
    weatherDescriptor = WeatherDescriptor.Rainy(scale),
    location = Location(city = "Bogotá D.C"),
    temperature = Temperature(current = "11°", low = "9°", high = "17°"),
    //color = Color.LightGray,
    color = Color.DarkGray,
    wind = "25.3 km/h"
)


data class ScheduledWeather(
    val screen: WeatherScreen,
    val weather: Weather,
    val partsOfDay: List<ScheduledWeather> = emptyList()
)

fun scheduledWeather(scale: PathScale) = listOf(
    ScheduledWeather(
        screen = WeatherScreen.Today,
        weather = todayWeather(scale),
        partsOfDay = listOf(
            ScheduledWeather(
                screen = WeatherScreen.Hourly("13:00"),
                weather = todayWeather(scale),
            ),
            ScheduledWeather(
                screen = WeatherScreen.Hourly("14:00"),
                weather = todayWeather(scale),
            ),
            ScheduledWeather(
                screen = WeatherScreen.Hourly("15:00"),
                weather = todayWeather(scale),
            ),
            ScheduledWeather(
                screen = WeatherScreen.Hourly("16:00"),
                weather = todayWeather(scale),
            )
        )
    ),
    ScheduledWeather(
        screen = WeatherScreen.Tomorrow,
        weather = tomorrowWeather(scale),
        partsOfDay = listOf(
            ScheduledWeather(
                screen = WeatherScreen.Hourly("13:00"),
                weather = tomorrowWeather(scale),
            ),
            ScheduledWeather(
                screen = WeatherScreen.Hourly("14:00"),
                weather = tomorrowWeather(scale),
            ),
            ScheduledWeather(
                screen = WeatherScreen.Hourly("15:00"),
                weather = tomorrowWeather(scale),
            ),
            ScheduledWeather(
                screen = WeatherScreen.Hourly("16:00"),
                weather = tomorrowWeather(scale),
            )
        )
    ),
    ScheduledWeather(
        screen = WeatherScreen.NextDays("Wednesday"),
        weather = dayThreeWeather(scale),
        partsOfDay = emptyList()
    ),
    ScheduledWeather(
        screen = WeatherScreen.NextDays("Thursday"),
        weather = dayFourWeather(scale),
        partsOfDay = emptyList()
    ),
    ScheduledWeather(
        screen = WeatherScreen.NextDays("Friday"),
        weather = dayFiveWeather(scale),
        partsOfDay = emptyList()
    ),
    ScheduledWeather(
        screen = WeatherScreen.NextDays("Saturday"),
        weather = daySixWeather(scale),
        partsOfDay = emptyList()
    ),
    ScheduledWeather(
        screen = WeatherScreen.NextDays("Sunday"),
        weather = daySevenWeather(scale),
        partsOfDay = emptyList()
    ),
    ScheduledWeather(
        screen = WeatherScreen.NextDays("Monday"),
        weather = dayEightWeather(scale),
        partsOfDay = emptyList()
    ),
)

sealed class WeatherScreen(val position: Int, val title: String) {
    object Today : WeatherScreen(0, "Today")
    object Tomorrow : WeatherScreen(1, "Tomorrow")
    data class NextDays(val day: String) : WeatherScreen(2, "Next Days") {
        override fun hashCode(): Int {
            return title.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NextDays

            if (title != other.title) return false

            return true
        }
    }
    data class Hourly(val hour: String) : WeatherScreen(-1, "Hourly")
}

