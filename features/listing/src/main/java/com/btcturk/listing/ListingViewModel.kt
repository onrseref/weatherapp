package com.btcturk.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.btcturk.data.dto.ticker.TickerResponse
import com.btcturk.data.util.Resource
import com.btcturk.domain.usecase.TickerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(private val tickerUseCase: TickerUseCase) :
    ViewModel() {

    private val eventChannel = Channel<TickerEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        fetchTicker()
    }

    fun fetchTicker() {
        viewModelScope.launch {
            tickerUseCase().onStart {
                eventChannel.send(TickerEvent.IsLoading(true))
            }.onCompletion {
                eventChannel.send(TickerEvent.IsLoading(false))
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        eventChannel.send(
                            TickerEvent.Error(
                                message = it.message,
                                messageText = it.messageText
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
        data class Error(val message: Int? = null, val messageText: String? = null) :
            TickerEvent()

        data class Success(val tickerResponse: TickerResponse?) : TickerEvent()
    }
}