package com.btcturk.data.dto.ticker

data class TickerResponse(
    var data: ArrayList<TickerData> = arrayListOf(),
    var success: Boolean? = null,
    var message: String? = null,
    var code: Int? = null
)