package com.example.staffapp.model

data class Weather(
    val name: String,
    val main: Main,
    val weather: List<WeatherDescription>
)

data class Main(
    val temp: Double
)

data class WeatherDescription(
    val description: String,
    val icon: String
)