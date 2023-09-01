package com.btcturk.data.di

import com.btcturk.data.repository.ticker.TickerRepository
import com.btcturk.data.repository.ticker.TickerRepositoryImpl
import com.btcturk.data.repository.kline.KlineRepository
import com.btcturk.data.repository.kline.KlineRepositoryImpl
import com.btcturk.data.service.KlineService
import com.btcturk.data.service.TickerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTickerRepository(tickerService: TickerService): TickerRepository {
        return TickerRepositoryImpl(tickerService)
    }

    @Provides
    @Singleton
    fun provideKlineRepository(klineService: KlineService): KlineRepository {
        return KlineRepositoryImpl(klineService)
    }
}