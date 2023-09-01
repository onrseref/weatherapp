package com.btcturk.listing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.btcturk.data.dto.ticker.TickerData
import com.btcturk.listing.databinding.ItemListingBinding
import java.text.DecimalFormat
import kotlin.math.round

class ListingAdapter(
    private val data: ArrayList<TickerData>,
    private val onItemClick: (TickerData?) -> Unit
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
        holder.bindData(data[position], onItemClick)
    }

    class ItemViewHolder(private val binding: ItemListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(tickerData: TickerData, onItemClick: (TickerData?) -> Unit) {
            val decimalFormat = DecimalFormat("0.000")

            binding.tvPair.text = tickerData.pair
            binding.tvLast.text = decimalFormat.format(tickerData.last)
            binding.tvVolume.text = decimalFormat.format(tickerData.volume)
            binding.tvDailyPercent.text = buildString {
                append("%")
                append(tickerData.dailyPercent.toString())
            }
            binding.tvNumeratorSymbol.text = tickerData.numeratorSymbol

            tickerData.dailyPercent?.let {
                if (it > 0) {
                    binding.tvDailyPercent.setTextColor(binding.tvDailyPercent.context.getColor(com.btcturk.base.R.color.green))
                } else {
                    binding.tvDailyPercent.setTextColor(binding.tvDailyPercent.context.getColor(com.btcturk.base.R.color.red))
                }
            }

            binding.root.setOnClickListener {
                onItemClick(tickerData)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}