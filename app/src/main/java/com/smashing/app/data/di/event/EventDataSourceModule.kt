package com.smashing.app.data.di.event

import com.smashing.app.data.remote.datasource.api.EventRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.EventRemoteDataSourceImpl
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
        eventDataSourceImpl: EventRemoteDataSourceImpl,
    ): EventRemoteDataSource
}
