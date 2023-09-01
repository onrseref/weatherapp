package com.btcturk.listing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.btcturk.data.dto.ticker.TickerData
import com.btcturk.data.local.PairEntity
import com.btcturk.listing.databinding.ItemFavoritesBinding
import java.text.DecimalFormat

class FavoritesAdapter(
    private val data: ArrayList<PairEntity>,
    private val onItemClick: (PairEntity?) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ItemFavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFavoritesViewHolder {
        return ItemFavoritesViewHolder(
            ItemFavoritesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemFavoritesViewHolder, position: Int) {
        holder.bindData(data[position], onItemClick)
    }

    class ItemFavoritesViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(pairEntity: PairEntity, onItemClick: (PairEntity?) -> Unit) {
            val decimalFormat = DecimalFormat("0.000")

            binding.tvPair.text = pairEntity.pair
            binding.tvLast.text = decimalFormat.format(pairEntity.last)
            binding.tvDailyPercent.text = buildString {
                append("%")
                append(pairEntity.dailyPercent)
            }

            pairEntity.dailyPercent?.let {
                if (it > 0) {
                    binding.tvDailyPercent.setTextColor(binding.tvDailyPercent.context.getColor(com.btcturk.base.R.color.green))
                } else {
                    binding.tvDailyPercent.setTextColor(binding.tvDailyPercent.context.getColor(com.btcturk.base.R.color.red))
                }
            }

            binding.root.setOnClickListener {
                onItemClick(pairEntity)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}