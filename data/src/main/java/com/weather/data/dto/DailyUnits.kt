package com.weather.data.dto

import com.google.gson.annotations.SerializedName

data class DailyUnits(
    @SerializedName("time") var time: String? = null,
    @SerializedName("temperature_2m_max") var temperature2mMax: String? = null,
    @SerializedName("temperature_2m_min") var temperature2mMin: String? = null
)