package com.weather.home.util

import android.content.Context
import com.weather.home.R
import java.text.SimpleDateFormat
import java.util.*

object Util {

    fun getWeatherType(weatherCode: Int?, context: Context): WeatherModel? {
        when (weatherCode) {
            0 -> {
                return WeatherModel(
                    context.getString(R.string.clear_sky),
                    R.drawable.ic_sunny,
                    com.weather.base.R.color.orange
                )
            }
            1, 2, 3 -> {
                return WeatherModel(
                    context.getString(R.string.overcast),
                    R.drawable.ic_overcast,
                    com.weather.base.R.color.gray
                )
            }
            45, 48 -> {
                return WeatherModel(
                    context.getString(R.string.clear_sky),
                    R.drawable.ic_foggy,
                    com.weather.base.R.color.sky
                )
            }
            51, 53, 55 -> {
                return WeatherModel(
                    context.getString(R.string.drizzle),
                    R.drawable.ic_drizzle,
                    com.weather.base.R.color.sky
                )
            }
            56, 57 -> {
                return WeatherModel(
                    context.getString(R.string.freezing_drizzle),
                    R.drawable.ic_drizzle,
                    com.weather.base.R.color.sky
                )
            }
            61, 63, 65 -> {
                return WeatherModel(
                    context.getString(R.string.rainy),
                    R.drawable.ic_rainy,
                    com.weather.base.R.color.sky
                )
            }
            66, 67 -> {
                return WeatherModel(
                    context.getString(R.string.freezing_rain), R.drawable.ic_rainy,
                    com.weather.base.R.color.sky
                )
            }
            71, 73, 75 -> {
                return WeatherModel(
                    context.getString(R.string.snow_fall), R.drawable.ic_snow,
                    com.weather.base.R.color.sky
                )
            }
            77 -> {
                return WeatherModel(
                    context.getString(R.string.snow_grains), R.drawable.ic_snow,
                    com.weather.base.R.color.sky
                )
            }
            80, 81, 82 -> {
                return WeatherModel(
                    context.getString(R.string.rain_showers), R.drawable.ic_rainy,
                    com.weather.base.R.color.sky
                )
            }
            85, 86 -> {
                return WeatherModel(
                    context.getString(R.string.snow_showers), R.drawable.ic_snow,
                    com.weather.base.R.color.sky
                )
            }
            95, 96, 99 -> {
                return WeatherModel(
                    context.getString(R.string.thunderstorm),
                    R.drawable.ic_thunderstorm,
                    com.weather.base.R.color.gray
                )
            }
        }
        return null
    }

    fun currentDateTime(dateInParse: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var date: Date? = null
        try {
            date = sdf.parse(dateInParse)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        val formatter = SimpleDateFormat("EE, MMM dd", Locale.getDefault())
        return formatter.format(date)
    }
}