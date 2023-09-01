package com.btcturk.domain.usecase

import com.btcturk.data.dto.ticker.TickerResponse
import com.btcturk.data.repository.ticker.TickerRepository
import com.btcturk.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TickerUseCase @Inject constructor(private val tickerRepository: TickerRepository) {

    suspend operator fun invoke(): Flow<Resource<TickerResponse>> = flow {
        tickerRepository.fetchTicker().collect { result ->
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