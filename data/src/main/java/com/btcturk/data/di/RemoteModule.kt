package com.btcturk.data.di

import com.btcturk.data.BuildConfig
import com.btcturk.data.interceptor.AuthenticationInterceptor
import com.btcturk.data.service.KlineService
import com.btcturk.data.service.TickerService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val SESSION_TIMEOUT = 120
    private const val BASE_URL_TICKER = BuildConfig.BASE_URL_TICKER
    private const val BASE_URL_KLINE = BuildConfig.BASE_URL_KLINE
    private const val NAME_TICKER = "Ticker"
    private const val NAME_KLINE = "Kline"

    @Provides
    @Singleton
    internal fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(AuthenticationInterceptor())
            .connectTimeout(SESSION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(SESSION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(SESSION_TIMEOUT.toLong(), TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    @Named(NAME_TICKER)
    internal fun provideRetrofitTicker(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_TICKER)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    @Named(NAME_KLINE)
    internal fun provideRetrofitKline(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_KLINE)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    internal fun provideTickerService(@Named(NAME_TICKER) retrofit: Retrofit): TickerService {
        return retrofit.create(TickerService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideKlineService(@Named(NAME_KLINE) retrofit: Retrofit): KlineService {
        return retrofit.create(KlineService::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}