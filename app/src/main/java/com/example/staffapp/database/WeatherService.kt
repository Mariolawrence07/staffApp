package com.example.staffapp.database

import com.example.staffapp.model.Weather
import retrofit2.Call
import retrofit2.http.GET

interface WeatherService {
    @GET("weather?lat=53.01&lon=-2.18&units=metric&APPID=73a2feb8bcc7e6828c161282601eca80")

    fun getWeather(): Call<Weather>
}
