package com.btcturk.data.local.db

import androidx.room.*
import com.btcturk.data.local.PairEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePairDAO {

    @Query("SELECT * FROM pair_table")
    fun fetchFavoritePairs(): Flow<List<PairEntity>>

    @Query("SELECT EXISTS(SELECT * FROM pair_table WHERE pair=(:pair))")
    suspend fun exist(pair: String?): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pairEntity: PairEntity)

    @Delete
    suspend fun delete(pairEntity: PairEntity)

}