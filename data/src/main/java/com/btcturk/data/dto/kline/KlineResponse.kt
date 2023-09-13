package com.btcturk.data.dto.kline

data class KlineResponse(
    val t: ArrayList<Double> = arrayListOf(),
    val c: ArrayList<Double> = arrayListOf(),
)