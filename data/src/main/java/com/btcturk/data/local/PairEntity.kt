package com.btcturk.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "pair_table", primaryKeys = ["pair", "last", "dailyPercent", "timestamp"])
data class PairEntity(
    @ColumnInfo(name = "pair") val pair: String,
    @ColumnInfo(name = "last") val last: Double,
    @ColumnInfo(name = "dailyPercent") val dailyPercent: Double,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)