package com.btcturk.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.btcturk.data.dto.ticker.TickerData
import com.btcturk.data.dto.ticker.TickerResponse
import com.btcturk.data.local.PairEntity
import com.btcturk.data.util.Resource
import com.btcturk.domain.usecase.FavoritesUseCase
import com.btcturk.domain.usecase.TickerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val tickerUseCase: TickerUseCase, private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val eventChannel = Channel<TickerEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        fetchFavoriteList()
        fetchTicker()
    }

    private fun fetchTicker() {
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

    fun fetchFavoriteList() {
        viewModelScope.launch {
            favoritesUseCase.getFavoritePairList().collect {
                eventChannel.send(TickerEvent.FavoriteList(it))
            }
        }
    }

    private fun insert(pairEntity: PairEntity) = viewModelScope.launch {
        favoritesUseCase.insertFavoritePair(pairEntity)
    }

    private fun remove(pairEntity: PairEntity) = viewModelScope.launch {
        favoritesUseCase.removeFavoritePair(pairEntity)
    }

    fun checkExistFavoritesAndAction(tickerData: TickerData) = viewModelScope.launch {
        val pairEntity = PairEntity(
            tickerData.pair ?: "",
            tickerData.last ?: 0.0,
            tickerData.dailyPercent ?: 0.0,
            tickerData.timestamp ?: 0
        )
        val isExist = favoritesUseCase.existFavoritePair(pairEntity)

        if (isExist) {
            remove(pairEntity)
            tickerData.isFavorite = false
            eventChannel.send(TickerEvent.HasAdded(false, tickerData))
        } else {
            insert(pairEntity)
            tickerData.isFavorite = true
            eventChannel.send(TickerEvent.HasAdded(true, tickerData))
        }
    }

    sealed class TickerEvent {
        data class IsLoading(var isLoading: Boolean) : TickerEvent()
        data class Error(val message: Int? = null, val messageText: String? = null) : TickerEvent()

        data class Success(val tickerResponse: TickerResponse?) : TickerEvent()
        data class FavoriteList(val favoriteList: List<PairEntity>) : TickerEvent()
        data class HasAdded(val hasAdded: Boolean, val tickerData: TickerData) : TickerEvent()
    }
}