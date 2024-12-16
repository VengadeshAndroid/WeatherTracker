package com.nooro.weather.webservice

import com.nooro.weather.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String?,
        @Query("q") location: String?
    ): Response<WeatherResponse>

}