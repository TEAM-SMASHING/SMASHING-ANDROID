package com.smashing.app.data.di.search

import com.smashing.app.data.repository.api.SearchRepository
import com.smashing.app.data.repository.impl.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl,
    ): SearchRepository
}
