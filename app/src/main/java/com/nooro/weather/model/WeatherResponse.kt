package com.nooro.weather.model

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val localtime: String
)

data class Current(
    val temp_c: Double,
    val temp_f: Double,
    val condition: Condition,
    val humidity: Int,
    val uv: Double,
    val feelslike_c: Double,
    val feelslike_f: Double
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Int
)
