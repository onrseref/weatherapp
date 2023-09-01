package com.btcturk.data.repository.ticker

import com.btcturk.data.dto.ticker.TickerResponse
import com.btcturk.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface TickerRepository {
    suspend fun fetchTicker(): Flow<Resource<TickerResponse>>
}