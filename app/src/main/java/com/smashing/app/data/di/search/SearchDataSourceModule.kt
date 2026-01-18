package com.smashing.app.data.di.search

import com.smashing.app.data.remote.datasource.api.SearchRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSearchRemoteDataSource(
        searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl
    ): SearchRemoteDataSource
}
