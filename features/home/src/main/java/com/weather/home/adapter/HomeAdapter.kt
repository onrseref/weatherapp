package com.weather.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.weather.home.R
import com.weather.home.databinding.ItemListingBinding
import java.text.DecimalFormat

class HomeAdapter(
    private val data: ArrayList<Int>,
    private val onItemClick: (Int?) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(data[position], onItemClick)
    }

    class ItemViewHolder(private val binding: ItemListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(
            weatherData: Int,
            onItemClick: (Int?) -> Unit
        ) {
            /*val decimalFormat = DecimalFormat("0.000")

            binding.tvPair.text = weatherData.pair
            binding.tvLast.text = decimalFormat.format(weatherData.last)
            binding.tvVolume.text = decimalFormat.format(weatherData.volume)
            binding.tvDailyPercent.text = buildString {
                append("%")
                append(weatherData.dailyPercent.toString())
            }
            binding.tvNumeratorSymbol.text = weatherData.numeratorSymbol

            weatherData.dailyPercent?.let {
                if (it > 0) {
                    binding.tvDailyPercent.setTextColor(binding.tvDailyPercent.context.getColor(com.weather.base.R.color.green))
                } else {
                    binding.tvDailyPercent.setTextColor(binding.tvDailyPercent.context.getColor(com.weather.base.R.color.red))
                }
            }*/

            binding.root.setOnClickListener {
                onItemClick(weatherData)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}