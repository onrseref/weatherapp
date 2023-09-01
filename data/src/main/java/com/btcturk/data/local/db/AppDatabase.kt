package com.btcturk.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.btcturk.data.local.PairEntity

@Database(
    entities = [PairEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME: String = "favorite_pairs"
    }

    abstract fun getFavoritePairDAO(): FavoritePairDAO
}