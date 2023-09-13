package com.weather.data.service

import com.weather.data.dto.WeatherResponse
import retrofit2.http.GET

interface WeatherService {

    @GET("/api/v2/ticker")
    suspend fun fetchWeather(): WeatherResponse
}