package com.weather.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.data.dto.Daily
import com.weather.home.databinding.ItemDailyBinding
import com.weather.home.util.Util

class HomeAdapter(
    private val daily: Daily,
    private val tempUnit: String?
) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemDailyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(
            daily.time[position],
            daily.weathercode[position],
            daily.temperature2mMax[position],
            daily.temperature2mMin[position],
            tempUnit
        )
    }

    class ItemViewHolder(private val binding: ItemDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(
            day: String,
            weatherCode: Int,
            maxTemp: Double,
            minTemp: Double,
            tempUnit: String?
        ) {

            with(binding) {
                tvDay.text = Util.currentDateTime(day)

                tvMaxTemp.text = buildString {
                    append(maxTemp.toString())
                    append(" ")
                    append(tempUnit)
                }
                tvMinTemp.text = buildString {
                    append(minTemp.toString())
                    append(" ")
                    append(tempUnit)
                }

                Util.getWeatherType(weatherCode, root.context)?.let {
                    ivWeather.setImageResource(it.weatherImage)
                }
            }
        }
    }

    override fun getItemCount(): Int = daily.time.size
}