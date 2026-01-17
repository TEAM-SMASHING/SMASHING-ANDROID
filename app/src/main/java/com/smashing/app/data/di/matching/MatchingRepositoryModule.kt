package com.smashing.app.data.di.matching

import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.data.repository.impl.MatchingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MatchingRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMatchingRepository(
        matchingRepositoryImpl: MatchingRepositoryImpl
    ): MatchingRepository
}
