package com.smashing.app.data.di.matching

import com.smashing.app.data.remote.datasource.api.MatchingRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.MatchingRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MatchingDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMatchingRemoteDataSource(
        matchingRemoteDataSourceImpl: MatchingRemoteDataSourceImpl
    ): MatchingRemoteDataSource
}
