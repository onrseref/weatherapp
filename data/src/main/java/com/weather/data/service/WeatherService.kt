package com.weather.data.service

import com.weather.data.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/v1/forecast")
    suspend fun fetchWeather(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("daily") daily: String,
        @Query("timezone") timezone: String,
        @Query("current_weather") currentWeather: Boolean
    ): WeatherResponse
}