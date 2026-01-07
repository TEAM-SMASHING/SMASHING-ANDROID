package com.smashing.app.data.di.matching

import com.smashing.app.data.remote.datasource.api.RemoteMatchingDataSource
import com.smashing.app.data.remote.datasource.impl.RemoteMatchingDataSourceImpl
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
    abstract fun bindRemoteMatchingDataSource(
        impl: RemoteMatchingDataSourceImpl,
    ): RemoteMatchingDataSource
}
