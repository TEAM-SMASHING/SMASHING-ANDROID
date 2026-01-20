package com.smashing.app.data.di.ranking

import com.smashing.app.data.remote.datasource.api.RankingRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.RankingRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RankingDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRankingRemoteDataSource(
        rankingRemoteDataSourceImpl: RankingRemoteDataSourceImpl
    ): RankingRemoteDataSource
}