package com.smashing.app.data.di.event

import com.smashing.app.data.remote.datasource.api.RemoteEventDataSource
import com.smashing.app.data.remote.datasource.impl.RemoteEventDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EventDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteEventDataSource(
        remoteEventDataSourceImpl: RemoteEventDataSourceImpl,
    ): RemoteEventDataSource
}
