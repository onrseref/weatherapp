package com.btcturk.domain.usecase

import com.btcturk.data.dto.kline.KlineResponse
import com.btcturk.data.repository.kline.KlineRepository
import com.btcturk.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KlineUseCase @Inject constructor(private val klineRepository: KlineRepository) {
    suspend operator fun invoke(pair: String, to: String): Flow<Resource<KlineResponse>> = flow {
        klineRepository.fetchKline(pair, to).collect { result ->
            when (result) {
                is Resource.Error -> {
                    emit(
                        Resource.Error(
                            message = result.message,
                            messageText = result.messageText
                        )
                    )
                }
                is Resource.Success -> {
                    emit(Resource.Success(result.data))
                }
            }
        }
    }
}