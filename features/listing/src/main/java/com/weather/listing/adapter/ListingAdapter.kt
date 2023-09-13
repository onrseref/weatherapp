package com.weather.listing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.weather.data.dto.WeatherData
import com.weather.listing.R
import com.weather.listing.databinding.ItemListingBinding
import java.text.DecimalFormat

class ListingAdapter(
    private val data: ArrayList<WeatherData>,
    private val onItemClick: (WeatherData?) -> Unit,
    private val onFavoriteClick: (WeatherData?) -> Unit
) : RecyclerView.Adapter<ListingAdapter.ItemViewHolder>() {

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
        holder.bindData(data[position], onItemClick, onFavoriteClick)
    }

    class ItemViewHolder(private val binding: ItemListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(
            weatherData: WeatherData,
            onItemClick: (WeatherData?) -> Unit,
            onFavoriteClick: (WeatherData?) -> Unit
        ) {
            if (weatherData.isFavorite) {
                binding.ivFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_favorite
                    )
                )
            } else {
                binding.ivFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_favorite_empty
                    )
                )
            }
            val decimalFormat = DecimalFormat("0.000")

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
            }

            binding.root.setOnClickListener {
                onItemClick(weatherData)
            }

            binding.ivFavorite.setOnClickListener {
                onFavoriteClick(weatherData)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}