package com.btcturk.data.dto.kline

data class KlineResponse(
    var t: ArrayList<Double> = arrayListOf(),
    var c: ArrayList<Double> = arrayListOf(),
)