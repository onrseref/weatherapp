package com.btcturk.data.di

import android.content.Context
import androidx.room.Room
import com.btcturk.data.local.db.AppDatabase
import com.btcturk.data.local.db.FavoritePairDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideFavoriteDogDAO(db: AppDatabase): FavoritePairDAO = db.getFavoritePairDAO()
}