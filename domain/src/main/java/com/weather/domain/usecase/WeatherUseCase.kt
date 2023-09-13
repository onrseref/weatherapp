package com.weather.domain.usecase

import com.weather.data.dto.WeatherResponse
import com.weather.data.repository.WeatherRepository
import com.weather.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(): Flow<Resource<WeatherResponse>> = flow {
        weatherRepository.fetchWeather().collect { result ->
            when (result) {
                is Resource.Error -> {
                    emit(
                        Resource.Error(
                            message = result.message,
                            messageText = result.messageText
                        )
                    )
                }
                is Resource.Success -> {
                    emit(Resource.Success(result.data))
                }
            }
        }
    }
}