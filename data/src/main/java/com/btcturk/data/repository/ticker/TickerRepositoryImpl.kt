package com.btcturk.data.repository.ticker

import com.btcturk.data.dto.ticker.TickerResponse
import com.btcturk.data.repository.ticker.TickerRepository
import com.btcturk.data.service.TickerService
import com.btcturk.data.util.Resource
import com.btcturk.data.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(private val tickerService: TickerService) :
    TickerRepository {
    override suspend fun fetchTicker(): Flow<Resource<TickerResponse>> {
        return flow {
            emit(safeApiCall(Dispatchers.IO) {
                tickerService.fetchTicker()
            })
        }
    }
}