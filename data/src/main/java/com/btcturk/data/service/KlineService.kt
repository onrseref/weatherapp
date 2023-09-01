package com.btcturk.data.service

import com.btcturk.data.dto.kline.KlineResponse
import com.btcturk.data.dto.ticker.TickerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KlineService {

    @GET("/v1/klines/history?")
    suspend fun fetchTicker(
        @Query("symbol") pair: String,
        @Query("resolution") resolution: String,
        @Query("to") to: String,
        @Query("from") from: String
    ): KlineResponse
}