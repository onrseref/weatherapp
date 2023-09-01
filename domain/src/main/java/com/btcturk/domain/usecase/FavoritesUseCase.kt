package com.btcturk.domain.usecase

import com.btcturk.data.local.PairEntity
import com.btcturk.data.repository.favorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {

    fun getFavoritePairList(): Flow<List<PairEntity>> = favoritesRepository.fetchFavoritePairList()

    suspend fun existFavoritePair(pairEntity: PairEntity): Boolean =
        favoritesRepository.exist(pairEntity)

    suspend fun insertFavoritePair(pairEntity: PairEntity) = favoritesRepository.insert(pairEntity)

    suspend fun removeFavoritePair(pairEntity: PairEntity) = favoritesRepository.delete(pairEntity)
}