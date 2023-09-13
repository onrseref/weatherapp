package com.weather.data.repository

import com.weather.data.dto.WeatherResponse
import com.weather.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchWeather(
        latitude: String,
        longitude: String
    ): Flow<Resource<WeatherResponse>>
}