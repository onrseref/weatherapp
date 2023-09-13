package com.weather.data.dto

data class WeatherResponse(
    var data: ArrayList<WeatherData> = arrayListOf(),
    var success: Boolean? = null,
    var message: String? = null,
    var code: Int? = null
)