package com.smashing.app.data.di.ranking

import com.smashing.app.data.remote.service.RankingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RankingServiceModule {

    @Provides
    @Singleton
    fun provideRankingService(
        retrofit: Retrofit
    ): RankingService = retrofit.create()
}