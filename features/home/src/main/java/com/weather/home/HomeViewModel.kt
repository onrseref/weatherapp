package com.weather.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.data.dto.WeatherResponse
import com.weather.data.util.Resource
import com.weather.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase) :
    ViewModel() {

    private val _weatherFlow = MutableStateFlow<HomeEvent?>(null)
    val weatherFlow = _weatherFlow.asStateFlow()

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            weatherUseCase().onStart {
                _weatherFlow.emit(HomeEvent.IsLoading(true))
            }.onCompletion {
                _weatherFlow.emit(HomeEvent.IsLoading(false))
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        _weatherFlow.emit(
                            HomeEvent.Error(
                                message = it.message, messageText = it.messageText
                            )
                        )
                    }
                    is Resource.Success -> {
                        _weatherFlow.emit(HomeEvent.Success(it.data))
                    }
                }
            }
        }
    }

    sealed class HomeEvent {
        data class IsLoading(var isLoading: Boolean) : HomeEvent()
        data class Error(val message: Int? = null, val messageText: String? = null) : HomeEvent()

        data class Success(val weatherResponse: WeatherResponse?) : HomeEvent()
    }
}