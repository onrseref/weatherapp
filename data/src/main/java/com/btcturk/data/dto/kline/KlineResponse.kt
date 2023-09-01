package com.btcturk.data.dto.kline

data class KlineResponse(
    var t: ArrayList<Int> = arrayListOf(),
    var c: ArrayList<Int> = arrayListOf(),
)