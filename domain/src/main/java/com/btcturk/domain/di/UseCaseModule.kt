package com.btcturk.domain.di

import com.btcturk.data.repository.ticker.TickerRepository
import com.btcturk.data.repository.kline.KlineRepository
import com.btcturk.domain.usecase.KlineUseCase
import com.btcturk.domain.usecase.TickerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideTickerUseCase(tickerRepository: TickerRepository): TickerUseCase {
        return TickerUseCase(tickerRepository)
    }

    @Provides
    @Singleton
    fun provideKlineUseCase(klineRepository: KlineRepository): KlineUseCase {
        return KlineUseCase(klineRepository)
    }
}