package com.weather.data.di

import com.google.gson.Gson
import com.weather.data.BuildConfig
import com.weather.data.interceptor.AuthenticationInterceptor
import com.weather.data.repository.WeatherRepository
import com.weather.data.repository.WeatherRepositoryImpl
import com.weather.data.service.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    internal fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(AuthenticationInterceptor())
            .connectTimeout(BuildConfig.SESSION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(BuildConfig.SESSION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(BuildConfig.SESSION_TIMEOUT.toLong(), TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    internal fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherService: WeatherService): WeatherRepository {
        return WeatherRepositoryImpl(weatherService)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}