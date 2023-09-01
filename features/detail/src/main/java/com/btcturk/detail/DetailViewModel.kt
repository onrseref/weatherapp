package com.btcturk.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.btcturk.data.dto.kline.KlineResponse
import com.btcturk.data.util.Resource
import com.btcturk.domain.usecase.KlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val klineUseCase: KlineUseCase) :
    ViewModel() {

    private val eventChannel = Channel<DetailEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun fetchKline(pair: String, to: String) {
        viewModelScope.launch {
            klineUseCase(pair, to).onStart {
                eventChannel.send(DetailEvent.IsLoading(true))
            }.onCompletion {
                eventChannel.send(DetailEvent.IsLoading(false))
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        eventChannel.send(
                            DetailEvent.Error(
                                message = it.message,
                                messageText = it.messageText
                            )
                        )
                    }
                    is Resource.Success -> {
                        eventChannel.send(DetailEvent.SuccessDetail(it.data))
                    }
                }
            }
        }
    }

    sealed class DetailEvent {
        data class IsLoading(var isLoading: Boolean) : DetailEvent()
        data class Error(val message: Int? = null, val messageText: String? = null) :
            DetailEvent()

        data class SuccessDetail(val klineResponse: KlineResponse?) : DetailEvent()
    }
}
