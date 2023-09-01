package com.btcturk.data.repository.kline

import com.btcturk.data.dto.kline.KlineResponse
import com.btcturk.data.dto.ticker.TickerResponse
import com.btcturk.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface KlineRepository {
    suspend fun fetchKline(pair: String, to: String): Flow<Resource<KlineResponse>>
}