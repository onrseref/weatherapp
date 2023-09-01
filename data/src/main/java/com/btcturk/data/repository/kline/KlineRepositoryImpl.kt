package com.btcturk.data.repository.kline

import com.btcturk.data.BuildConfig
import com.btcturk.data.dto.kline.KlineResponse
import com.btcturk.data.dto.ticker.TickerResponse
import com.btcturk.data.service.KlineService
import com.btcturk.data.util.Resource
import com.btcturk.data.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KlineRepositoryImpl @Inject constructor(private val klineService: KlineService) :
    KlineRepository {
    override suspend fun fetchKline(pair: String, to: String): Flow<Resource<KlineResponse>> {
        return flow {
            emit(safeApiCall(Dispatchers.IO) {
                klineService.fetchTicker(pair, BuildConfig.RESOLUTION, to, "1602925320")
            })
        }
    }
}