package com.weather.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("generationtime_ms") var generationtimeMs: Double? = null,
    @SerializedName("utc_offset_seconds") var utcOffsetSeconds: Int? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_abbreviation") var timezoneAbbreviation: String? = null,
    @SerializedName("elevation") var elevation: Int? = null,
    @SerializedName("current_weather") var currentWeather: CurrentWeather? = null,
    @SerializedName("daily_units") var dailyUnits: DailyUnits? = null,
    @SerializedName("daily") var daily: Daily? = null
)