package com.btcturk.data.repository.favorites

import com.btcturk.data.local.db.FavoritePairDAO
import com.btcturk.data.local.PairEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val favoritePairDAO: FavoritePairDAO) {

    fun fetchFavoritePairList(): Flow<List<PairEntity>> =
        favoritePairDAO.fetchFavoritePairs()

    suspend fun exist(pairEntity: PairEntity): Boolean =
        favoritePairDAO.exist(pairEntity.pair)

    suspend fun insert(pairEntity: PairEntity) = favoritePairDAO.insert(pairEntity)

    suspend fun delete(pairEntity: PairEntity) = favoritePairDAO.delete(pairEntity)


}
