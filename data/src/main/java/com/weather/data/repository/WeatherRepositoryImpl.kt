package com.weather.data.repository

import com.weather.data.dto.WeatherResponse
import com.weather.data.service.WeatherService
import com.weather.data.util.Resource
import com.weather.data.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherService: WeatherService) :
    WeatherRepository {
    override suspend fun fetchWeather(): Flow<Resource<WeatherResponse>> {
        return flow {
            emit(safeApiCall(Dispatchers.IO) {
                weatherService.fetchWeather()
            })
        }
    }
}