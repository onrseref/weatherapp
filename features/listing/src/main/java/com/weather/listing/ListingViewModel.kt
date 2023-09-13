package com.weather.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.data.dto.WeatherResponse
import com.weather.data.util.Resource
import com.weather.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase) : ViewModel() {

    private val eventChannel = Channel<TickerEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        fetchTicker()
    }

    private fun fetchTicker() {
        viewModelScope.launch {
            weatherUseCase().onStart {
                eventChannel.send(TickerEvent.IsLoading(true))
            }.onCompletion {
                eventChannel.send(TickerEvent.IsLoading(false))
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        eventChannel.send(
                            TickerEvent.Error(
                                message = it.message, messageText = it.messageText
                            )
                        )
                    }
                    is Resource.Success -> {
                        eventChannel.send(TickerEvent.Success(it.data))
                    }
                }
            }
        }
    }

    sealed class TickerEvent {
        data class IsLoading(var isLoading: Boolean) : TickerEvent()
        data class Error(val message: Int? = null, val messageText: String? = null) : TickerEvent()

        data class Success(val weatherResponse: WeatherResponse?) : TickerEvent()
    }
}