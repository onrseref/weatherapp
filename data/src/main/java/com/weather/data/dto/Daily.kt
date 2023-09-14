package com.weather.data.dto

import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("time") var time: ArrayList<String> = arrayListOf(),
    @SerializedName("temperature_2m_max") var temperature2mMax: ArrayList<Double> = arrayListOf(),
    @SerializedName("temperature_2m_min") var temperature2mMin: ArrayList<Double> = arrayListOf(),
    @SerializedName("weathercode") var weathercode: ArrayList<Int> = arrayListOf()
)