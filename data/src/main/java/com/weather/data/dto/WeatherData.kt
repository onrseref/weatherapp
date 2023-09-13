package com.weather.data.dto

data class WeatherData(
    var pair: String? = null,
    var pairNormalized: String? = null,
    var last: Double? = null,
    var volume: Double? = null,
    var dailyPercent: Double? = null,
    var numeratorSymbol: String? = null,
    var timestamp: Long? = null,
    var isFavorite: Boolean = false
)