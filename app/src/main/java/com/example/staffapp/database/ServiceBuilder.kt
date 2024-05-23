package com.example.staffapp.database

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val URL ="https://api.openweathermap.org/data/2.5/"
    private val okHttp = OkHttpClient.Builder()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
        .build()

    fun <T> buildService (serviceType :Class<T>):T{
        return retrofit.create(serviceType)
    }
}
