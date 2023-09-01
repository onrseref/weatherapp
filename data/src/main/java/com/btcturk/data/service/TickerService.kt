package com.btcturk.data.service

import com.btcturk.data.dto.ticker.TickerResponse
import retrofit2.http.GET

interface TickerService {

    @GET("/api/v2/ticker")
    suspend fun fetchTicker(): TickerResponse
}